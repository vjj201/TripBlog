package com.java017.tripblog.controller.user;

import com.java017.tripblog.entity.PasswordResetToken;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.MailService;
import com.java017.tripblog.service.PasswordResetTokenService;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/10/12 - 上午 02:03
 */

@RestController
public class MailController {


    private final MailService mailService;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public MailController(MailService mailService, UserService userService, PasswordResetTokenService passwordResetTokenService) {
        this.mailService = mailService;
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    //發送註冊驗證信件
    @GetMapping("/sendSignupMail")
    public void sendSignUpMail(HttpSession session) {
        mailService.sendSignupMail(session);
        System.out.println("發送信件");
    }

    //驗證註冊信件
    @PostMapping("/checkSignupMail")
    public boolean checkSignUpMail(@RequestParam String code, HttpSession session) {
        System.out.println("使用者輸入的驗證:" + code);
        boolean result = mailService.verifySignupCode(code, session);

        if (result) {
            System.out.println("驗證成功");
            User user = (User) session.getAttribute("user");

            User updateUser = userService.findUserById(user.getId());
            updateUser.setMailVerified(true);
            userService.updateUser(updateUser);
            return true;
        } else {
            System.out.println("驗證失敗");
            return false;
        }
    }

    //發送重設密碼信件
    @GetMapping("/sendPasswordResetMail")
    public void sendPasswordResetMail(@RequestParam String email, HttpServletRequest request) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            System.out.println("未找到重設密碼的用戶");
            return;
        }

        String UUID = mailService.createUUID();
        String link = mailService.generateTokenLink(request, UUID);
        System.out.println("ResetLink:" + link);

        //存入token
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByUser(user);

        if (passwordResetToken == null) {
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setResetCount(0);
        }

        int resetCount = passwordResetToken.getResetCount();
        if(resetCount >= 3) {
            System.out.println("超過當天發送次數");
            return;
        }

        passwordResetToken.setToken(UUID);
        passwordResetToken.setUser(user);
        passwordResetToken.setResetCount(++resetCount);
        passwordResetToken.setExpiryDate(new Date());
        passwordResetTokenService.createOrUpdateToken(passwordResetToken);

        //發送信件
        mailService.sendPasswordResetMail(user.getEmail(), user.getNickname(), link);
    }
}
