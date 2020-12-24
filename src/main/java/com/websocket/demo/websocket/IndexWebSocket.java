package com.websocket.demo.websocket;

import com.alibaba.fastjson.JSON;
import com.websocket.demo.model.MsgDto;
import com.websocket.demo.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static com.websocket.demo.util.UserUtil.getUsername;

@Slf4j
@Component // 注册到spring
@ServerEndpoint("/ws/{userId}") // 创建websocket服务
public class IndexWebSocket {

    /**
     * 连接成功响应
     */
    @OnOpen
    public void openSession(@PathParam("userId") Integer userId, Session session) {
        if (UserUtil.findById(userId) == null) {
            log.info("客户端: [" + userId + "] : 用户不存在！");
            return;
        }
        WebSocketUtils.ONLINE_USER_SESSIONS.put(userId, session);
        log.info("客户端: [" + getUsername(userId) + "] : 连接成功！");
        WebSocketUtils.sendMessageAll("客户端: [" + getUsername(userId) + "] : 连接成功！");
    }

    /**
     * 收到消息响应
     */
    @OnMessage
    public void onMessage(@PathParam("userId") Integer userId, String message) {

        log.info("服务器收到：[" + getUsername(userId) + "] : " + message);
        MsgDto dto = JSON.parseObject(message, MsgDto.class);
        dto.setContent("[" + getUsername(dto.getUserId()) + "] : " + dto.getContent());
        WebSocketUtils.sendMessageSingle(dto);
    }

    /**
     * 连接关闭响应
     */
    @OnClose
    public void onClose(@PathParam("userId") Integer userId, Session session) throws IOException {
        //当前的Session 移除
        WebSocketUtils.ONLINE_USER_SESSIONS.remove(userId);
        log.info("[" + getUsername(userId) + "] : 断开连接！");
        //并且通知其他人当前用户已经断开连接了
        WebSocketUtils.sendMessageAll("[" + getUsername(userId) + "] : 断开连接！");
        session.close();
    }

    /**
     * 连接异常响应
     */
    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        session.close();
    }
}
