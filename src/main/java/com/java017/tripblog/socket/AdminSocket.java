package com.java017.tripblog.socket;

import com.java017.tripblog.entity.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YuCheng
 * @date 2021/11/17 - 下午 11:02
 */

@Component
@ServerEndpoint(value = "/admin/socket", configurator = GetHttpSessionConfigurator.class)
public class AdminSocket {

    private static final Logger log = LoggerFactory.getLogger(AdminSocket.class);

    private static final Map<String, AdminSocket> webSocketSet = new ConcurrentHashMap<>();

    private static int onlineCount;

    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        User user = (User) httpSession.getAttribute("user");
        webSocketSet.put(user.getUsername(), this);
        addOnlineCount();
        log.info("【websocket】帳號:" + user.getUsername() + "連線，在線數:{}", getOnlineCount());
    }

    @OnClose
    public void onClose() {
        User user = (User) httpSession.getAttribute("user");
        webSocketSet.remove(user.getUsername());
        subOnlineCount();
        log.info("【websocket】帳號:" + user.getUsername() + "下線，在線數:{}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) {
        User user = (User) httpSession.getAttribute("user");
        log.info("【websocket】收到客戶端:" + user.getUsername() + "發來的訊息:{}", message);
    }

    //訂單廣播
    public static void sendInfo(String message) {
        log.info("【websocket】廣播訊息, message={}", message);
        for (String username : webSocketSet.keySet()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", message);
                webSocketSet.get(username).session.getAsyncRemote().sendText(jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        AdminSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        AdminSocket.onlineCount--;
    }
}

