package org.jeecg.modules.games.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.NumberUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.games.configure.WebsocketService;
import org.jeecg.modules.games.entity.CgRooms;
import org.jeecg.modules.games.entity.CgRoomsUsers;
import org.jeecg.modules.games.mapper.RoomsMapper;
import org.jeecg.modules.games.query.*;
import org.jeecg.modules.games.service.RoomsService;
//import org.jeecg.modules.games.utils.SortMapByKey;
import org.jeecg.modules.games.vo.CgRoomsDetailVO;
import org.jeecg.modules.games.vo.NoticeUserOperateQuery;
import org.jeecg.modules.games.vo.SynchronousDataVO;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.vo.SysUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomsServiceImpl implements RoomsService {

    @Autowired
    private RoomsMapper roomsMapper;
    @Autowired
    private WebsocketService websocketService;
    @Autowired
    private RedisUtil redisUtil;

    private String url = "https://test.text.com/";

    @Override
    public List<CgRooms> getListRoom(String userId) {
        return roomsMapper.getRoomDetailByHomeowner(userId);
    }

    @Override
    @Transactional
    public Result<?> createRoom(CgRooms cgRooms) {
        Result<String> result = new Result<>();
        if (cgRooms.getIsCustom() == 1) {
            //随机url,8位
            cgRooms.setUrl(this.url + getRandomString(8));
        } else {
            cgRooms.setUrl(this.url + cgRooms.getUrl());
        }

        try {
            cgRooms.setStatus(1);
            roomsMapper.insert(cgRooms);
            result.setCode(200);
            result.setMessage("ルームを作成しました");
            result.setSuccess(true);
            result.setResult(cgRooms.getId());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    @Transactional
    public Result<?> deleteRoom(String roomId) {
        Result<?> result = new Result<>();
        try {
            roomsMapper.deleteById(roomId);
            result.success("削除しました");
        } catch (Exception e) {
            e.printStackTrace();
            result.error500("server error");
        }
        return result;
    }

    @Override
    public Result<CgRoomsDetailVO> getRoomDetail(String roomId) {
        Result<CgRoomsDetailVO> result = new Result<>();
        try {
            CgRoomsDetailVO cgRoomsDetailVO = roomsMapper.getRoomDetail(roomId);
            cgRoomsDetailVO.setUrl(cgRoomsDetailVO.getUrl().split(this.url)[1]);
            result.setCode(200);
            result.setSuccess(true);
            result.setResult(cgRoomsDetailVO);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    @Transactional
    public Result<?> insertRoomUser(List<RoomUserQuery> roomUserQuery) {
        Result<?> result = new Result<>();
        try {
            CgRoomsDetailVO cgRooms = roomsMapper.getRoomDetail(roomUserQuery.get(0).getRoomId());
            String roomUrl[] = cgRooms.getUrl().split(this.url);
            roomsMapper.insertRoomUser(roomUserQuery);
            roomsMapper.updateRoomsStatus(cgRooms.getUrl(), 2);
            result.setCode(200);
            result.setMessage("記録しました");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    @Transactional
    public Result<?> insertInvitationLog(InvitationLogQuery invitationLogQuery) {
        Result<?> result = new Result<>();
        try {
            int count = roomsMapper.getInvitationLog(invitationLogQuery.getRoomId(),invitationLogQuery.getUserId());
            if (count != 0){
                result.setCode(200);
                result.setMessage("招待記録を追加しました");
                result.setSuccess(true);
                return result;
            }
            roomsMapper.insertInvitationLog(invitationLogQuery);
            result.setCode(200);
            result.setMessage("招待記録を追加しました");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public PageInfo<CgRoomsDetailVO> getGamesRecord(GamesRecordQuery gamesRecordQuery) {
        //开启分页
        PageHelper.startPage(gamesRecordQuery.getPageNo(), gamesRecordQuery.getPageSize());
        //查询数据
        List<CgRoomsDetailVO> list = roomsMapper.getGamesRecord(gamesRecordQuery);
        //封装
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo<CgRoomsDetailVO> getInvitationRecord(GamesRecordQuery gamesRecordQuery) {
        //开启分页
        PageHelper.startPage(gamesRecordQuery.getPageNo(), gamesRecordQuery.getPageSize());
        //查询数据
        List<CgRoomsDetailVO> list = roomsMapper.getInvitationRecord(gamesRecordQuery);
        list.stream().forEach(item -> {
            if (item.getHomeownerId().equals(gamesRecordQuery.getUserId())) {
                item.setIsHomeownerFlag(1);
            } else {
                item.setIsHomeownerFlag(0);
            }
        });
        //封装
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public Result<CgRooms> getRoomDetailByUrlAndHomeowner(String url, String homeowner) {
        Result<CgRooms> result = new Result<>();
        try {
            String urls = this.url + url;
            CgRooms cgRooms = roomsMapper.getRoomDetailByUrlAndHomeowner(urls, homeowner);
            if (cgRooms != null) {
                result.setCode(200);
                result.setResult(cgRooms);
                result.setSuccess(true);
            } else {
                result.setCode(400);
                result.setMessage("ワークスペースが見つかりません");
                result.setSuccess(false);
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result<?> gamesOperate(GamesOperateQuery gamesOperateQuery) {
        JSONObject jsonObject = new JSONObject();
        switch (gamesOperateQuery.getOperateType()) {
            case 1:
                JSONObject object = getPai(gamesOperateQuery.getNoCreatNum());
                jsonObject.put("username", gamesOperateQuery.getUsername());
                jsonObject.put("userId", gamesOperateQuery.getUserId());
                jsonObject.put("operateMsg", "从卡堆抽牌");
                jsonObject.put("number", object.getInteger("creatNum"));
                jsonObject.put("noCreatNum", object.getJSONArray("noCreatNum"));
                break;
            case 2:
                jsonObject.put("username", gamesOperateQuery.getUsername());
                jsonObject.put("userId", gamesOperateQuery.getUserId());
                jsonObject.put("operateMsg", "复制其他用户的牌");
                jsonObject.put("number", gamesOperateQuery.getNumber());
                break;
            case 3:
                jsonObject.put("username", gamesOperateQuery.getUsername());
                jsonObject.put("userId", gamesOperateQuery.getUserId());
                jsonObject.put("operateMsg", "从弃卡堆里抽牌");
                jsonObject.put("number", gamesOperateQuery.getNumber());
                break;
            case 4:
                jsonObject.put("username", gamesOperateQuery.getUsername());
                jsonObject.put("userId", gamesOperateQuery.getUserId());
                jsonObject.put("operateMsg", "弃牌");
                jsonObject.put("foldNumber", gamesOperateQuery.getFoldNumber());
                break;
            case 5:
                jsonObject.put("operateMsg", "游戏暂停");
                break;
            case 6:
                jsonObject.put("usernames", gamesOperateQuery.getUsernames());
                jsonObject.put("userIds", gamesOperateQuery.getUserIds());
                jsonObject.put("operateMsg", "跳过用户");
                break;
            case 7:
                jsonObject.put("usernames", gamesOperateQuery.getUsernames());
                jsonObject.put("userIds", gamesOperateQuery.getUserIds());
                jsonObject.put("operateMsg", "解除跳过用户");
                jsonObject.put("number", gamesOperateQuery.getNumber());
                break;
            case 8:
                jsonObject.put("operateMsg", "游戏结束");
                roomsMapper.updateRoomsStatus(this.url + gamesOperateQuery.getUrl(), 3);
                //游戏结束，删除玩家邀请记录
                roomsMapper.deleteInviteLog(gamesOperateQuery.getRoomId());
                break;
            case 9:
                jsonObject.put("username", gamesOperateQuery.getUsername());
                jsonObject.put("userId", gamesOperateQuery.getUserId());
                jsonObject.put("operateMsg", "跳过此次操作");
                break;
            case 10:
                jsonObject.put("username", gamesOperateQuery.getUsername());
                jsonObject.put("userId", gamesOperateQuery.getUserId());
                jsonObject.put("operateMsg", "替换手牌");
                jsonObject.put("number", gamesOperateQuery.getNumber());
                break;
            case 11:
                jsonObject.put("operateMsg", "继续游戏");
                break;
            case 12:
                jsonObject.put("operateMsg", "下一用户操作");
                //查询房间的参与用户
                List<String> strings = gamesOperateQuery.getUserIds();
                List<CgRoomsUsers> roomsUsers = roomsMapper.getRoomUsers(gamesOperateQuery.getRoomId());
                List<String> allExistIds = new ArrayList<>();
                roomsUsers.stream().forEach(item->{
                    allExistIds.add(item.getUserId());
                });
                strings.stream().forEach(item->{
                    int index = allExistIds.indexOf(item);
                    if (index != -1){
                        roomsUsers.remove(index);
                    }
                });
                for (int i = 0; i < roomsUsers.size(); i++) {
                    if (gamesOperateQuery.getUserId().equals(roomsUsers.get(i).getUserId())) {
                        if (i == roomsUsers.size() - 1) {
                            jsonObject.put("userId", roomsUsers.get(0).getUserId());
                        } else {
                            jsonObject.put("userId", roomsUsers.get(i + 1).getUserId());
                        }
                    }
                }
                break;
            case 13:
                Map<String, Object> map = new HashMap<>();
                map.put("roomId", gamesOperateQuery.getRoomId());
                map.put("customize",gamesOperateQuery.getCustomize());
                redisUtil.hmset(gamesOperateQuery.getRoomId(), map);
                break;
        }
        jsonObject.put("customize", gamesOperateQuery.getCustomize());
        websocketService.broadcast(gamesOperateQuery.getUrl(), jsonObject.toJSONString());
        return Result.ok();
    }

    //生成随机字符串
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public JSONObject getPai(List<Integer> integers) {
        JSONObject jsonObject = new JSONObject();
        int currentData;
        if (integers.size() > 1) {
            int index = (int) (Math.random() * integers.size());
            currentData = integers.get(index);
        } else {
            currentData = integers.get(0);
        }
        List<Integer> integerList = NumberUtil.iteratorDelete(integers, currentData);
        jsonObject.put("creatNum", currentData);
        jsonObject.put("noCreatNum", integerList);
        return jsonObject;
    }

    /**
     * 开始分配牌
     */
//    @Transactional(readOnly = false)
//    public JSONObject fenpai(String Url, List<RoomUserQuery> personalNum) {
//        //按照顺序进行排序发牌
//        Collections.sort(personalNum);
//        //每个人固定为7张牌
//        int number = 7;
//
//        List<Integer> numberList = new ArrayList<>();
////        Map<String, Object> mEndMap = new HashMap<>();
//        TreeMap<Integer, Object> mEndMap = new TreeMap<>();
//        //List<Set<Integer>> mEndList = new ArrayList<>();
//        for (int i = 1; i <= 89; i++) {
//            numberList.add(i);
//        }
//        Random r = new Random();
//        //int count = personalNum;
//        //personalNum.stream().forEach(item ->
//        for (int i = 0; i < personalNum.size(); i++) {
//            Set<Integer> result = new HashSet<Integer>(7);
//            while (result.size() < number) {
//                int index = (int) (Math.random() * numberList.size());
//                int currentData = numberList.get(index);
//                NumberUtil.iteratorDelete(numberList, currentData);
//                if (result.contains(currentData)) {
//                    continue;
//                }
//                result.add(currentData);
//            }
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("userId", personalNum.get(i).getUserId());
//            map.put("username", personalNum.get(i).getUsername());
//            map.put("email", personalNum.get(i).getEmail());
//            map.put("sequence", personalNum.get(i).getSequence());
//            map.put("result", result);
//            mEndMap.put(personalNum.get(i).getSequence(), map);
//        }
//        //分配完成之后，将分配结果告知socket
//        JSONObject jsonObjectWs = new JSONObject();
//        jsonObjectWs.put("result", JSONObject.toJSONString(mEndMap));
//        jsonObjectWs.put("operateMsg", "游戏开始，向每位玩家分牌");
//        websocketService.broadcast(Url, jsonObjectWs.toJSONString());
////        while(count-->0){
////            Set<Integer> result = new HashSet<Integer>(7);
////            while(result.size()< number){
////                int index=(int)(Math.random()*numberList.size());
////                int currentData = numberList.get(index);
////                NumberUtil.iteratorDelete(numberList,currentData);
////                if(result.contains(currentData)){
////                    continue;
////                }
////                result.add(currentData);
////            }
////            mEndMap.put() .add(result);
////        })
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("createdNum", mEndMap);
//        jsonObject.put("noCreatNum", numberList);
//
//        return jsonObject;
//    }

    /**
     * 开始分配牌
     */
    @Transactional(readOnly = false)
    public JSONObject fenpai(String Url, List<RoomUserQuery> personalNum) {
        //按照顺序进行排序发牌
        Collections.sort(personalNum);
        //每个人固定为7张牌
        int number = 7;

        List<Integer> numberList = new ArrayList<>();
        TreeMap<String, Object> mEndMap = new TreeMap<>();
        //List<Set<Integer>> mEndList = new ArrayList<>();
        for (int i = 1; i <= 89; i++) {
            numberList.add(i);
        }
        Random r = new Random();
        //int count = personalNum;
        //personalNum.stream().forEach(item ->
        for (int i = 0; i < personalNum.size(); i++) {
            Set<Integer> result = new HashSet<Integer>(7);
            while (result.size() < number) {
                int index = (int) (Math.random() * numberList.size());
                int currentData = numberList.get(index);
                NumberUtil.iteratorDelete(numberList, currentData);
                if (result.contains(currentData)) {
                    continue;
                }
                result.add(currentData);
            }
//            mEndMap.put("userId", personalNum.get(i).getUserId());
//            mEndMap.put("username", personalNum.get(i).getUsername());
//            mEndMap.put("email", personalNum.get(i).getEmail());
            mEndMap.put(personalNum.get(i).getUserId(), result);
//            mEndMap.put(String.valueOf(personalNum.get(i).getSequence()), personalNum.get(i).getSequence());
        }
//        SortMapByKey.sortMapByKey(mEndMap);
//        Map<String, Object> mapResult = new HashMap<>();
        LinkedHashMap<String, Object> mapResult = new LinkedHashMap<>();
        for (int i = 0; i < personalNum.size(); i++) {
            JSONArray result = JSONArray.parseArray(mEndMap.get(personalNum.get(i).getUserId()).toString());
            mapResult.put(personalNum.get(i).getUserId(), result);
        }

        //分配完成之后，将分配结果告知socket
        JSONObject jsonObjectWs = new JSONObject();
        jsonObjectWs.put("result", JSONObject.toJSONString(mapResult));
        jsonObjectWs.put("operateMsg", "游戏开始，向每位玩家分牌");
        websocketService.broadcast(Url, jsonObjectWs.toJSONString());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("createdNum", mapResult);
        jsonObject.put("noCreatNum", numberList);

        return jsonObject;
    }

    @Override
    public Result<SysUserInfoVo> getUserInfoById(String userId) {
        Result<SysUserInfoVo> result = new Result<>();
        try {
            SysUserInfoVo userInfoVo = roomsMapper.getUserInfoById(userId);
            result.setCode(200);
            result.setResult(userInfoVo);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result<?> noticeUserOperate(NoticeUserOperateQuery noticeUserOperateQuery) {
        List<CgRoomsUsers> roomsUsers = roomsMapper.getRoomUsers(noticeUserOperateQuery.getRoomId());
        CgRoomsDetailVO cgRoomsDetailVO = roomsMapper.getRoomDetail(noticeUserOperateQuery.getRoomId());
        int currentSequence = roomsMapper.getSequence(noticeUserOperateQuery.getRoomId(), noticeUserOperateQuery.getUserId());
        JSONObject jsonObject = new JSONObject();
        if (currentSequence == roomsUsers.size()) {
            jsonObject.put("userId", roomsUsers.get(0).getUserId());
            jsonObject.put("operaMsg", "下一轮操作");
        } else {
            String noticeUserId = "";
            for (int i = 0; i < roomsUsers.size(); i++) {
                if (roomsUsers.get(i).getSequence() == currentSequence) {
                    noticeUserId = roomsUsers.get(i + 1).getUserId();
                }
            }
            jsonObject.put("userId", noticeUserId);
            jsonObject.put("operaMsg", "下一轮操作");
        }
        websocketService.broadcast(cgRoomsDetailVO.getUrl().split(this.url)[1], jsonObject.toJSONString());
        return Result.ok("操作しました");
    }

    @Override
    public Result<?> inRoomFlagByRoomIdUserId(String roomId, String userId) {
        Result<?> result = new Result<>();
        try {
            int countFlag = roomsMapper.inRoomFlagByRoomIdUserId(roomId, userId);
            if (countFlag > 0) {
                result.setCode(200);
                result.setMessage("ワークスペースは存在しています");
                result.setSuccess(true);
                return result;
            }
            result.setCode(400);
            result.setMessage("ワークスペースは存在していません");
            result.setSuccess(false);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result<SynchronousDataVO> synchronousData(String roomId) {
        Result<SynchronousDataVO> result = new Result<>();
        try {
            Map<Object, Object> map = redisUtil.hmget(roomId);
            if (map.size() == 0) {
                result.setCode(405);
                result.setMessage("データなし");
                result.setSuccess(false);
                return result;
            }
            List<UserCardNumbersQuery> userCardNumbers = null;
            if (map.containsKey("userCardNumbers")) {
                userCardNumbers = JSONObject.parseArray(map.get("userCardNumbers").toString()).toJavaList(UserCardNumbersQuery.class);
            }
            String roomIdRedis = null;
            if (map.containsKey("roomId")) {
                roomIdRedis = map.get("roomId").toString();
            }

            String countDown = null;
            if (map.containsKey("countDown")) {
                countDown = map.get("countDown").toString();
            }
            String gameTime = null;
            if (map.containsKey("gameTime")) {
                gameTime = map.get("gameTime").toString();
            }

            String customize = null;
            if (map.containsKey("customize")) {
                customize = map.get("customize").toString();
            }

            int onlineCount = 0;
            if (map.containsKey("onlineCount")) {
                onlineCount = Integer.parseInt(map.get("onlineCount").toString());
            }

            List<Integer> foldNumbers = null;
            if (map.containsKey("foldNumbers")) {
                foldNumbers = JSONObject.parseArray(map.get("foldNumbers").toString()).toJavaList(Integer.class);
            }

            List<Integer> cardNumbers = null;
            if (map.containsKey("cardNumbers")) {
                cardNumbers = JSONObject.parseArray(map.get("cardNumbers").toString()).toJavaList(Integer.class);
            }

            SynchronousDataVO synchronousDataVO = SynchronousDataVO.builder()
                    .cardNumbers(cardNumbers)
                    .foldNumbers(foldNumbers)
                    .userCards(userCardNumbers)
                    .roomIdRedis(roomIdRedis)
                    .countDown(countDown)
                    .customize(customize)
                    .gameTime(gameTime)
                    .onlineCount(onlineCount)
                    .build();
            result.setSuccess(true);
            result.setCode(200);
            result.setResult(synchronousDataVO);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result<?> getInviteLog(String userId) {
        Result<Integer> result = new Result<>();
        try {
            int count = roomsMapper.getInviteLog(userId);
            result.setCode(200);
            result.setResult(count);
            result.setSuccess(false);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result<?> updateUserName(SysUser sysUser) {
        Result<?> result = new Result<>();
        try {
            roomsMapper.updateUserName(sysUser);
            result.setCode(200);
            result.setSuccess(false);
            return result;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result<?> updatePassword(UpdatePasswordQuery updatePasswordQuery) {
        SysUserInfoVo sysUser = roomsMapper.getUserInfoByEmail(updatePasswordQuery.getEmail());
        String password = PasswordUtil.encrypt(sysUser.getEmail(), updatePasswordQuery.getPassword(), sysUser.getSalt());
        updatePasswordQuery.setPassword(password);
        Result<?> result = new Result<>();
        String redisKey = updatePasswordQuery.getEmail()+":updatePasswordCode";
        if (!redisUtil.hasKey(redisKey)){
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("验证码已过期");
            return result;
        }
        if (redisUtil.hasKey(redisKey)){
            String redisCode = redisUtil.get(redisKey).toString();
            if (!redisCode.equals(updatePasswordQuery.getCode())){
                result.setCode(500);
                result.setSuccess(false);
                result.setMessage("验证码不匹配");
                return result;
            }
        }
        try {
            roomsMapper.updatePassword(updatePasswordQuery);
            result.setCode(200);
            result.setSuccess(false);
            return result;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.setCode(500);
            result.setMessage("サーバーと通信できませんでした");
            result.setSuccess(true);
            return result;
        }
    }
}
