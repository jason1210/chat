package com.websocket.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

@Slf4j
@Component // 注册到spring
@ServerEndpoint("/ws/{username}") // 创建websocket服务
public class IndexWebSocket {

    /**
     * 连接成功响应
     */
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        WebSocketUtils.ONLINE_USER_SESSIONS.put(username, session);
        log.info("客户端: [" + username + "] : 连接成功！");
        WebSocketUtils.sendMessageAll("客户端: [" + username + "] : 连接成功！");
    }

    /**
     * 收到消息响应
     */
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
        log.info("服务器收到：[" + username + "] : " + message);
        WebSocketUtils.sendMessageAll("[" + username + "] : " + message);
    }

    /**
     * 连接关闭响应
     */
    @OnClose
    public void onClose(@PathParam("username") String username, Session session) throws IOException {
        //当前的Session 移除
        WebSocketUtils.ONLINE_USER_SESSIONS.remove(username);
        log.info("[" + username + "] : 断开连接！");
        //并且通知其他人当前用户已经断开连接了
        WebSocketUtils.sendMessageAll("[" + username + "] : 断开连接！");
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
