package com.java017.tripblog.controller;

import com.java017.tripblog.util.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:23
 */

@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    private final CaptchaUtils captchaUtils;

    @Autowired
    public CaptchaController(CaptchaUtils captchaUtils) {
        this.captchaUtils = captchaUtils;
    }

    @GetMapping("/code")
    public void makeCaptchaCode(HttpSession session, HttpServletResponse response) {
        try {
            captchaUtils.makeCaptchaCode(session, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
