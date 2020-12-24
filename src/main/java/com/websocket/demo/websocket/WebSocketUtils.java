package com.websocket.demo.websocket;

import com.alibaba.fastjson.JSON;
import com.websocket.demo.model.MsgDto;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtils {
    public static final Map<Integer, Session> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();

    // 单用户推送
    public static void sendMessage(Session session, String message) {
        if (session == null) { return; }
        RemoteEndpoint.Async async = session.getAsyncRemote();
        if (async == null) { return; }
        async.sendText(message);
    }

    // 单用户推送
    public static void sendMessageSingle(MsgDto dto) {
        sendMessage(ONLINE_USER_SESSIONS.get(dto.getUserId()), dto.getContent());
        sendMessage(ONLINE_USER_SESSIONS.get(dto.getToUser()), dto.getContent());
    }

    // 全用户推送
    public static void sendMessageAll(String message) {
        ONLINE_USER_SESSIONS.forEach((sessionId, session) -> {
            sendMessage(session, message);
        });
    }
}
