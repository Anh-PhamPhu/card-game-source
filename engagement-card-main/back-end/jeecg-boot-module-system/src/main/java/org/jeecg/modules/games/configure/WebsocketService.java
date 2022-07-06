package org.jeecg.modules.games.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
//import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.games.query.GamesOperateQuery;
import org.jeecg.modules.games.service.RoomsService;
import org.jeecg.modules.games.vo.UserInfoVO;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{url}/{username}")
@Slf4j
@Component
public class WebsocketService {

    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap<>();

    private static final Map<String, String> users = new ConcurrentHashMap<>();

    private static ISysUserService userService;

    private static RedisUtil redisUtil;

    private static RoomsService roomsService;

    //当前房间在线人数
    private int onlineCount;

    //这里需要将此服务层的bean手动注入
    @Autowired
    public void setRepository(ISysUserService userService) {
        WebsocketService.userService = userService;
    }

    @Autowired
    public void setRepository(RedisUtil redisUtil) {
        WebsocketService.redisUtil = redisUtil;
    }

    @Autowired
    public void setRepository(RoomsService roomsService) {
        WebsocketService.roomsService = roomsService;
    }

    @OnOpen
    public void onOpen(@PathParam("url") String url, @PathParam("username") String username, Session session) {
        if (!rooms.containsKey(url)) {
            //不存在创建房间
            Set<Session> user = new HashSet<>();
            user.add(session);
            users.put(session.getId(), username);
            rooms.put(url, user);
        } else {
            //已存在房间，直接添加用户
            rooms.get(url).add(session);
        }
        this.onlineCount = rooms.get(url).size();
        SysUser sysUser = userService.getUserByName(username);
        if (sysUser != null) {
            UserInfoVO userInfoVO = UserInfoVO.builder()
                    .id(sysUser.getId())
                    .email(sysUser.getEmail())
                    .realname(sysUser.getRealname())
                    .username(sysUser.getNickname())
                    .userId(sysUser.getId())
                    .lineStatus("online")
                    .onlineNumber(rooms.get(url).size())
                    .build();
            broadcast(url, JSON.toJSONString(userInfoVO));
        } else {
            onClose(url, username, session);
        }
    }

    @OnClose
    public void onClose(@PathParam("url") String url, @PathParam("username") String username, Session session) {
        users.remove(session.getId());
        if (rooms.get(url).size() != 0) {
            rooms.get(url).remove(session);
        } else {
            //该房间没人了，要删除
            rooms.remove(url);
        }
        this.onlineCount = rooms.get(url).size();
        SysUser sysUser = userService.getUserByName(username);
        if (sysUser != null) {
            UserInfoVO userInfoVO = UserInfoVO.builder()
                    .id(sysUser.getId())
                    .email(sysUser.getEmail())
                    .realname(sysUser.getRealname())
                    .username(sysUser.getUsername())
                    .userId(sysUser.getId())
                    .lineStatus("offline")
                    .onlineNumber(rooms.get(url).size())
                    .build();
            broadcast(url, JSON.toJSONString(userInfoVO));
        }
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam("url") String url, @PathParam("username") String username) {
        log.error(error.getMessage(), error);
        SysUser sysUser = userService.getUserByName(username);
        if (sysUser == null) {
            broadcast(url, "未找到用户：" + username);
        } else {
            broadcast(url, "发生连接错误");
        }
        onClose(url, username, session);
    }

    @OnMessage
    public void onMessage(String message) {
        GamesOperateQuery gamesOperateQuery = JSON.parseObject(message, GamesOperateQuery.class);
        roomsService.gamesOperate(gamesOperateQuery);
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", gamesOperateQuery.getRoomId());
        map.put("customize",gamesOperateQuery.getCustomize());
//        map.put("userCardNumbers", JSONObject.toJSON(gamesOperateQuery.getUserCardNumbersQueries()).toString());
//        map.put("foldNumbers", gamesOperateQuery.getFoldNumbers());
//        map.put("cardNumbers", gamesOperateQuery.getCardNumbers());
//        map.put("gameTime", gamesOperateQuery.getGameTime());
//        map.put("countDown", gamesOperateQuery.getCountDown());
        map.put("onlineCount", this.onlineCount);
        redisUtil.hmset(gamesOperateQuery.getRoomId(), map);
    }

    //向房间广播有人在线
    public void broadcast(String url, String msg) {
        rooms.get(url).stream().forEach(item -> {
            try {
                item.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
