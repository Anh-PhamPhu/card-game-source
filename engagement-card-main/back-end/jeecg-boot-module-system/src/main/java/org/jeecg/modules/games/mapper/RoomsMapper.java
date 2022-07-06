package org.jeecg.modules.games.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.games.entity.CgRooms;
import org.jeecg.modules.games.entity.CgRoomsUsers;
import org.jeecg.modules.games.query.GamesRecordQuery;
import org.jeecg.modules.games.query.InvitationLogQuery;
import org.jeecg.modules.games.query.RoomUserQuery;
import org.jeecg.modules.games.query.UpdatePasswordQuery;
import org.jeecg.modules.games.vo.CgRoomsDetailVO;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.vo.SysUserInfoVo;

import java.util.List;

public interface RoomsMapper extends BaseMapper<CgRooms> {

    /*房间详情*/
    CgRoomsDetailVO getRoomDetail(String roomId);

    /* 查询此用户创建了多少房间 */
    List<CgRooms> getRoomDetailByHomeowner(@Param("homeowner") String homeowner);

    /*开始游戏，记录房间和参赛者*/
    int insertRoomUser(@Param("list") List<RoomUserQuery> roomUserQueries);

    /*生成邀请记录*/
    int insertInvitationLog(InvitationLogQuery invitationLogQuery);

    /*参加游戏记录*/
    List<CgRoomsDetailVO> getGamesRecord(GamesRecordQuery gamesRecordQuery);

    /*参加游戏记录*/
    List<CgRoomsDetailVO> getInvitationRecord(GamesRecordQuery gamesRecordQuery);

    /*根据房间url和房主用户id查询房间信息*/
    CgRooms getRoomDetailByUrlAndHomeowner(@Param("url") String url, @Param("homeowner") String homeowner);

    /*查询用户信息*/
    SysUserInfoVo getUserInfoById(String userId);

    /*查询房间的参与用户*/
    List<CgRoomsUsers> getRoomUsers(String roomId);

    /*查询用户在该游戏的顺序*/
    int getSequence(@Param("roomId") String roomId,@Param("userId") String userId);

    /*修改房间状态*/
    int updateRoomsStatus(@Param("roomUrl") String roomUrl, @Param("status") int status);

    /*通过用户id和房间id判断用户是否在房间内*/
    int inRoomFlagByRoomIdUserId(@Param("roomId") String roomId, @Param("userId") String userId);

    /*查询邀请记录的数量*/
    int getInviteLog(String userId);

    /*游戏结束删除邀请记录*/
    int deleteInviteLog(String roomId);

    /*修改用户昵称*/
    int updateUserName(SysUser sysUser);

    /*查询是否邀请过*/
    int getInvitationLog(@Param("roomId") String roomId,@Param("userId") String userId);

    /*修改密码*/
    int updatePassword(UpdatePasswordQuery updatePasswordQuery);

    /*根据邮箱查询用户信息*/
    SysUserInfoVo getUserInfoByEmail(String email);
}
