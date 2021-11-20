package com.java017.tripblog.controller.admin;

import com.java017.tripblog.socket.UserSocket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author YuCheng
 * @date 2021/11/20 - 下午 01:03
 */

@RestController
@RequestMapping("/admin")
public class SocketController {

    @GetMapping("/onlineUserSet")
    public ResponseEntity<Set<String>> getAllOnlineUser() {
        if(UserSocket.getOnlineCount() > 0) {
            return new ResponseEntity<>(UserSocket.getAllOnlineUsername(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
