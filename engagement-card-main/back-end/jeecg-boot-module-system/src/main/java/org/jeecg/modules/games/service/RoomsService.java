package org.jeecg.modules.games.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.games.entity.CgRooms;
import org.jeecg.modules.games.query.*;
import org.jeecg.modules.games.vo.CgRoomsDetailVO;
import org.jeecg.modules.games.vo.NoticeUserOperateQuery;
import org.jeecg.modules.games.vo.SynchronousDataVO;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.vo.SysUserInfoVo;

import java.util.List;

public interface RoomsService {

    /*创建房间*/
    Result<?> createRoom(CgRooms cgRooms);

    /*用户创建了多少未开始的房间*/
    List<CgRooms> getListRoom(String userId);

    /*房间详情*/
    Result<CgRoomsDetailVO> getRoomDetail(String roomId);

    /*删除房间*/
    Result<?> deleteRoom(String roomId);

    /*开始游戏，记录房间和参赛者*/
    Result<?> insertRoomUser(List<RoomUserQuery> roomUserQuery);

    /*生成邀请记录*/
    Result<?> insertInvitationLog(InvitationLogQuery invitationLogQuery);

    /*参加游戏记录*/
    PageInfo<CgRoomsDetailVO> getGamesRecord(GamesRecordQuery gamesRecordQuery);

    /*邀请记录*/
    PageInfo<CgRoomsDetailVO> getInvitationRecord(GamesRecordQuery gamesRecordQuery);

    /*根据房间url和房主用户id查询房间信息*/
    Result<CgRooms> getRoomDetailByUrlAndHomeowner(String url, String homeowner);

    /*游戏操作*/
    Result<?> gamesOperate(GamesOperateQuery gamesOperateQuery);

    /*分配牌*/
    JSONObject fenpai(String Url, List<RoomUserQuery> personalNum);

    Result<SysUserInfoVo> getUserInfoById(String userId);

    /*通知下个用户操作*/
    Result<?> noticeUserOperate(NoticeUserOperateQuery noticeUserOperateQuery);

    /*通过用户id和房间id判断用户是否在房间内*/
    Result<?> inRoomFlagByRoomIdUserId(String roomId, String userId);

    /*同步数据*/
    Result<SynchronousDataVO> synchronousData(String roomId);

    /*查询邀请记录的数量*/
    Result<?> getInviteLog(String userId);

    /*修改用户昵称*/
    Result<?> updateUserName(SysUser sysUser);

    /*修改密码*/
    Result<?> updatePassword(UpdatePasswordQuery updatePasswordQuery);
}
