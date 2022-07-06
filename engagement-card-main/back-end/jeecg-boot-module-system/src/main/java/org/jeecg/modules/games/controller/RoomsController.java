package org.jeecg.modules.games.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.NumberUtil;
import org.jeecg.modules.games.dto.AddResultParam;
import org.jeecg.modules.games.entity.CgResult;
import org.jeecg.modules.games.entity.CgRooms;
import org.jeecg.modules.games.entity.SysUserOrder;
import org.jeecg.modules.games.query.*;
import org.jeecg.modules.games.service.CgResultService;
import org.jeecg.modules.games.service.RoomsService;
import org.jeecg.modules.games.service.SysUserOrderService;
import org.jeecg.modules.games.vo.CgResultVO;
import org.jeecg.modules.games.vo.CgRoomsDetailVO;
import org.jeecg.modules.games.vo.NoticeUserOperateQuery;
import org.jeecg.modules.games.vo.SynchronousDataVO;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.system.vo.SysUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Title: DuplicateCheckAction
 * @Description: 重复校验工具
 * @Author 张代浩
 * @Date 2019-03-25
 * @Version V1.0
 */
@Slf4j
@Api(tags = "房间-创建房间")
@RestController
@RequestMapping("/games/rooms")
@Validated
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserOrderService sysUserOrderService;

    @Autowired
    private CgResultService cgResultService;

    @AutoLog(value = "房间-创建房间-创建人数限制")
    @ApiOperation(value = "房间-创建房间-创建人数限制[0:只能选择2人,1: 可以选择多人]", notes = "房间-创建房间-创建人数限制[0:只能选择2人,1: 可以选择多人]")
    @PostMapping("/createRoomPersonCount")
    public Result<Integer> createRoomPersonCount(HttpServletRequest httpServletRequest) {
        Result<Integer> result = new Result<>();

        String username = JwtUtil.getUsername(httpServletRequest.getHeader(DefContants.X_ACCESS_TOKEN));
        SysUser sysUser =  sysUserService.getUserByName(username);
        if(ObjectUtils.isEmpty(sysUser)){
            return result.error500("用户为空");
        }
        if(StringUtils.isBlank(sysUser.getCustomerId())){
            result.setSuccess(true);
            result.setCode(200);
            result.setResult(0);
            return result;
        }
        SysUserOrder sysUserOrder = sysUserOrderService.getSysUserOrderByUserId(sysUser.getCustomerId());
        if(ObjectUtils.isEmpty(sysUserOrder)){
            //这说明这个人没有订阅过
            result.setSuccess(true);
            result.setCode(200);
            result.setResult(0);
        }else {
            result.setSuccess(true);
            result.setCode(200);
            result.setResult(1);
        }
        return result;
    }



    @AutoLog(value = "房间-创建房间")
    @ApiOperation(value = "房间-创建房间", notes = "房间-创建房间")
    @PostMapping("/createRoom")
    public Result<?> createRoom(@RequestBody CgRooms cgRooms,HttpServletRequest httpServletRequest) {
        Result<?> result = new Result<>();
        String username = JwtUtil.getUsername(httpServletRequest.getHeader(DefContants.X_ACCESS_TOKEN));
        SysUser sysUser =  sysUserService.getUserByName(username);
        if(ObjectUtils.isEmpty(sysUser)){
            return result.error500("用户为空");
        }
        List<CgRooms> list = roomsService.getListRoom(sysUser.getId());
        if(list.size() > 0) {
            if (StringUtils.isBlank(sysUser.getCustomerId())) {
                //设置一个
                return result.error500("無料ユーザーは最大1つのルームしか作成できません");
            }
            SysUserOrder sysUserOrder = sysUserOrderService.getSysUserOrderByUserId(sysUser.getCustomerId());
            if (ObjectUtils.isEmpty(sysUserOrder)) {
                //这说明这个人没有订阅过
                return result.error500("無料ユーザーは最大1つのルームしか作成できません");
            } else {
                if(sysUserOrder.getProId().equals("prod_LlZwsOKPOn0TCt")){
                    result.error500("無料ユーザーは最大1つのルームしか作成できません");
                }else{
                    if(list.size() > 4) {
                        result.error500("無料ユーザーは最大5つのルームしか作成できません");
                    }
                }

            }
        }
        result = roomsService.createRoom(cgRooms);
        return result;
    }

    @AutoLog(value = "房间-房间待机室")
    @ApiOperation(value = "房间-房间待机室", notes = "房间-房间待机室")
    @GetMapping("/getRoomDetail/{roomId}")
    public Result<CgRoomsDetailVO> getRoomDetail(@PathVariable("roomId") String roomId) {

        return roomsService.getRoomDetail(roomId);
    }

    @AutoLog(value = "房间-删除房间")
    @ApiOperation(value = "房间-删除房间", notes = "房间-删除房间")
    @DeleteMapping("/deleteRoom/{roomId}")
    public Result<?> deleteRoom(@PathVariable("roomId") String roomId) {
        return roomsService.deleteRoom(roomId);
    }


    @AutoLog(value = "房间-开始游戏")
    @ApiOperation(value = "房间-开始游戏", notes = "房间-开始游戏")
    @PostMapping("/insertRoomUser")
    public Result<JSONObject> insertRoomUser(@RequestBody List<RoomUserQuery> roomUserQueries) {
        //在点击开始游戏按钮的时候就开始分发牌
        Result<JSONObject> result = new Result<>();
        //参数每个房间的参与人数
        //根据房间id找到房间url
        Result<CgRoomsDetailVO> cgRoomsDetailVO = this.getRoomDetail(roomUserQueries.get(0).getRoomId());

        JSONObject jsonObject = roomsService.fenpai(cgRoomsDetailVO.getResult().getUrl(), roomUserQueries);
        roomsService.insertRoomUser(roomUserQueries);
        result.setCode(200);
        result.setSuccess(true);
        result.setResult(jsonObject);
        return result;
    }


    @AutoLog(value = "参加游戏流程-生成邀请记录")
    @ApiOperation(value = "参加游戏流程-生成邀请记录", notes = "参加游戏流程-生成邀请记录")
    @PostMapping("/insertInvitationLog")
    public Result<?> insertInvitationLog(@RequestBody InvitationLogQuery invitationLogQuery) {
        return roomsService.insertInvitationLog(invitationLogQuery);
    }

    @AutoLog(value = "参加游戏流程-生成游戏记录")
    @ApiOperation(value = "参加游戏流程-生成游戏记录", notes = "参加游戏流程-生成游戏记录")
    @PostMapping("/addGamesRecord")
    public Result<?> addGamesRecord(@RequestBody List<AddResultParam> addResultParamList) {
        Result<?> result = new Result<>();
        Boolean isAdd = cgResultService.addList(addResultParamList);
        if(isAdd){
            return result.success("添加成功");
        }
        return result.error500("添加失败");
    }

    @AutoLog(value = "参加游戏流程-游戏记录详情")
    @ApiOperation(value = "参加游戏流程-游戏记录详情", notes = "参加游戏流程-游戏记录详情")
    @GetMapping("/getGamesRecordDetail")
    public Result<List<CgResultVO>> getGamesRecordDetail(@RequestParam(value = "roomId",required = true) @NotBlank(message = "房间不能为空") String roomId,HttpServletRequest httpServletRequest) {
        Result<List<CgResultVO>> result = new Result<>();
        String username = JwtUtil.getUsername(httpServletRequest.getHeader(DefContants.X_ACCESS_TOKEN));
        SysUser sysUser =  sysUserService.getUserByName(username);
        if(ObjectUtils.isEmpty(sysUser)){
            return result.error500("用户为空");
        }
        List<CgResultVO> isAdd = cgResultService.getDetail(roomId,sysUser.getCustomerId());
        result.setSuccess(true);
        result.setCode(200);
        result.setResult(isAdd);
        return result;
    }

    @AutoLog(value = "参加游戏流程-游戏记录")
    @ApiOperation(value = "参加游戏流程-游戏记录", notes = "参加游戏流程-游戏记录")
    @PostMapping("/getGamesRecord")
    public Result<PageInfo<CgRoomsDetailVO>> getGamesRecord(@RequestBody GamesRecordQuery gamesRecordQuery) {
        Result<PageInfo<CgRoomsDetailVO>> result = new Result<>();
        if (gamesRecordQuery.getPageNo() == 0 || gamesRecordQuery.getPageSize() == 0) {
            result.setSuccess(false);
            result.setCode(400);
            result.setMessage("分页参数不能为空");
            return result;
        }
        PageInfo<CgRoomsDetailVO> pageInfo = roomsService.getGamesRecord(gamesRecordQuery);
        result.setResult(pageInfo);
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    @AutoLog(value = "参加游戏流程-邀请记录")
    @ApiOperation(value = "参加游戏流程-邀请记录", notes = "参加游戏流程-邀请记录")
    @PostMapping("/getInvitationRecord")
    public Result<PageInfo<CgRoomsDetailVO>> getInvitationRecord(@RequestBody GamesRecordQuery gamesRecordQuery) {
        Result<PageInfo<CgRoomsDetailVO>> result = new Result<>();
        if (gamesRecordQuery.getPageNo() == 0 || gamesRecordQuery.getPageSize() == 0) {
            result.setSuccess(false);
            result.setCode(400);
            result.setMessage("分页参数不能为空");
            return result;
        }
        PageInfo<CgRoomsDetailVO> pageInfo = roomsService.getInvitationRecord(gamesRecordQuery);
        result.setResult(pageInfo);
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    @AutoLog(value = "房间-根据房间url后缀和房主用户id查询房间信息")
    @ApiOperation(value = "房间-根据房间url后缀和房主用户id查询房间信息", notes = "房间-根据房间url后缀和房主用户id查询房间信息")
    @GetMapping("/getRoomDetailByUrlAndHomeowner/{url}/{homeowner}")
    public Result<CgRooms> getRoomDetailByUrlAndHomeowner(@PathVariable("url") String url, @PathVariable("homeowner") String homeowner) {
        return roomsService.getRoomDetailByUrlAndHomeowner(url, homeowner);
    }

    @AutoLog(value = "参加游戏流程-游戏操作")
    @ApiOperation(value = "参加游戏流程-游戏操作", notes = "参加游戏流程-游戏操作")
    @PostMapping("/gamesOperate")
    public Result<?> gamesOperate(@RequestBody GamesOperateQuery gamesRecordQuery) {
        return roomsService.gamesOperate(gamesRecordQuery);
    }

    @AutoLog(value = "房间-查询用户信息")
    @ApiOperation(value = "房间-查询用户信息", notes = "房间-查询用户信息")
    @GetMapping("/getUserInfoById/{userId}")
    public Result<SysUserInfoVo> getUserInfoById(@PathVariable("userId") String userId) {
        return roomsService.getUserInfoById(userId);
    }

    @AutoLog(value = "参加游戏流程-提示用户操作（废弃）")
    @ApiOperation(value = "参加游戏流程-提示用户操作（废弃）", notes = "参加游戏流程-提示用户操作（废弃）")
    @PostMapping("/noticeUserOperate")
    public Result<?> noticeUserOperate(@RequestBody NoticeUserOperateQuery noticeUserOperateQuery) {
        return roomsService.noticeUserOperate(noticeUserOperateQuery);
    }

    @AutoLog(value = "房间-判断用户是否在该房间（参与游戏）")
    @ApiOperation(value = "房间-判断用户是否在该房间（参与游戏）", notes = "房间-判断用户是否在该房间（参与游戏）")
    @GetMapping("/inRoomFlagByRoomIdUserId/{roomId}/{userId}")
    public Result<?> inRoomFlagByRoomIdUserId(@PathVariable("roomId") String roomId, @PathVariable("userId") String userId) {
        return roomsService.inRoomFlagByRoomIdUserId(roomId, userId);
    }

    @AutoLog(value = "房间-同步数据")
    @ApiOperation(value = "房间-同步数据", notes = "房间-同步数据")
    @GetMapping("/synchronousData/{roomId}")
    public Result<SynchronousDataVO> synchronousData(@PathVariable("roomId") String roomId) {
        return roomsService.synchronousData(roomId);
    }

    @AutoLog(value = "房间-查询邀请但没参加的房间数量")
    @ApiOperation(value = "房间-查询邀请但没参加的房间数量", notes = "房间-查询邀请但没参加的房间数量")
    @GetMapping("/getInviteLog/{userId}")
    public Result<?> getInviteLog(@PathVariable("userId") String userId) {
        return roomsService.getInviteLog(userId);
    }

    @AutoLog(value = "房间-修改用户昵称")
    @ApiOperation(value = "房间-修改用户昵称", notes = "房间-修改用户昵称")
    @PostMapping("/updateUserName")
    public Result<?> updateUserName(@RequestBody SysUser sysUser) {
        return roomsService.updateUserName(sysUser);
    }

    @AutoLog(value = "房间-修改用户密码")
    @ApiOperation(value = "房间-修改用户密码", notes = "房间-修改用户密码")
    @PostMapping("/updatePassword")
    public Result<?> updatePassword(@RequestBody UpdatePasswordQuery sysUser) {
        return roomsService.updatePassword(sysUser);
    }
}
