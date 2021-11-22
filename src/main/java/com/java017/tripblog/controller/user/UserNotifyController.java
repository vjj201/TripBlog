package com.java017.tripblog.controller.user;

import com.java017.tripblog.entity.Notify;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/20 - 下午 04:24
 */

@RestController
@RequestMapping("/user")
public class UserNotifyController {
    private final NotifyService notifyService;

    @Autowired
    public UserNotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @GetMapping("/notifyList")
    public ResponseEntity<List<Notify>> findAllNotifyByUsername(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Notify> notifyList = notifyService.findAllByUsername(user.getUsername());
        return notifyList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(notifyList, HttpStatus.OK);
    }

    @DeleteMapping("/notify/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        try {
            notifyService.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/notifyCount")
    public ResponseEntity<Long> countAllNotifyByUsername(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Long count = notifyService.count(user.getUsername(),false);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PutMapping("/notify/{id}")
    public ResponseEntity<HttpStatus> readNotify(@PathVariable Long id) {
        Notify notify = notifyService.findById(id);
        if(ObjectUtils.isEmpty(notify)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        notify.setAlreadyRead(true);
        try {
            notifyService.updateNotify(notify);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
