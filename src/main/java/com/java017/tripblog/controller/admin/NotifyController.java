package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Notify;
import com.java017.tripblog.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YuCheng
 * @date 2021/11/20 - 下午 03:39
 */

@RestController
@RequestMapping("/admin")
public class NotifyController {

    private final NotifyService notifyService;

    @Autowired
    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @PostMapping("/notify")
    public ResponseEntity<HttpStatus> createNotify(@RequestBody Notify notify) {
        boolean result = notifyService.createNotify(notify);
        return result ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
