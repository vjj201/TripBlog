package com.java017.tripblog.socket;

import com.java017.tripblog.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YuCheng
 * @date 2021/11/17 - 下午 11:02
 */

@Component
@ServerEndpoint(value = "/user/socket", configurator = GetHttpSessionConfigurator.class)
public class UserSocket {

    private static final Map<String, UserSocket> webSocket = new ConcurrentHashMap<>();

    private static int onlineCount;

    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        User user = (User) httpSession.getAttribute("user");

        if(webSocket.containsKey(user.getUsername())) {
            return;
        }
        webSocket.put(user.getUsername(), this);
        addOnlineCount();
        AdminSocket.sendInfo(Integer.toString(getOnlineCount()));
        System.out.println("會員帳號:" + user.getUsername() + "上線，商城在線人數:" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        User user = (User) httpSession.getAttribute("user");
        webSocket.remove(user.getUsername());
        if(getOnlineCount() <= 0) {
            return;
        }
        subOnlineCount();
    }

    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        UserSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        UserSocket.onlineCount--;
    }
}

