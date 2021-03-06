package com.java017.tripblog.controller.user;

import com.java017.tripblog.entity.PasswordResetToken;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.MailService;
import com.java017.tripblog.service.PasswordResetTokenService;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/10/12 - 上午 02:03
 */

@Controller
public class MailController {

    private final int maxResetCountPerDay = 3;

    private final MailService mailService;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MailController(MailService mailService,
                          UserService userService,
                          PasswordResetTokenService passwordResetTokenService,
                          PasswordEncoder passwordEncoder ) {
        this.mailService = mailService;
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    //發送註冊驗證信件
    @GetMapping("/sendSignupMail")
    @ResponseBody
    public void sendSignUpMail(HttpSession session) {
        mailService.sendSignupMail(session);
        System.out.println("發送信件");
    }

    //驗證註冊信件
    @PostMapping("/checkSignupMail")
    @ResponseBody
    public boolean checkSignUpMail(@RequestParam String code, HttpSession session) {
        System.out.println("使用者輸入的驗證:" + code);
        boolean result = mailService.verifySignupCode(code, session);

        if (result) {
            System.out.println("驗證成功");
            User user = (User) session.getAttribute("user");
            session.invalidate();
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
    @PostMapping("/sendPasswordResetMail")
    @ResponseBody
    public String sendPasswordResetMail(@RequestParam String email,
                                        @RequestParam String imageCode,
                                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        String captcha = (String) session.getAttribute("captcha");

        session.removeAttribute("captcha");
        if (captcha == null || !captcha.equalsIgnoreCase(imageCode)) {
            System.out.println("code:" + captcha + "/" + imageCode);
            return "{\"result\" : \"驗證碼錯誤\"}";
        }

        System.out.println("email:" + email);
        User user = userService.findUserByEmail(email);
        if (user == null) {
            System.out.println("未找到重設密碼的用戶");
            return "{\"result\" : \"信箱錯誤\"}";
        }

        //存入token
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByUser(user);

        if (passwordResetToken == null) {
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setExpiryDate(new Date());
        }

        long time = passwordResetToken.getExpiryDate().getTime();
        long now = new Date().getTime();

        //重設當天發送上限
        if (now - time > 24 * 60 * 60 * 1000) {
            passwordResetToken.setResetCount(0);
        }

        Integer resetCount = passwordResetToken.getResetCount();

        if(resetCount == null) {
            resetCount = 0;
        }

        if (resetCount >= maxResetCountPerDay) {
            System.out.println("超過當日發送次數" + maxResetCountPerDay);
            return "{\"result\" : \"已超過當日發送次數\"}";
        }

        String UUID = mailService.createUUID();
        String link = mailService.generateTokenLink(request, UUID);
        System.out.println("ResetLink:" + link);

        passwordResetToken.setToken(UUID);
        passwordResetToken.setUser(user);
        passwordResetToken.setResetCount(++resetCount);
        passwordResetToken.setExpiryDate(new Date());
        passwordResetTokenService.createOrUpdateToken(passwordResetToken);

        //發送信件
        mailService.sendPasswordResetMail(user.getEmail(), user.getNickname(), link);
        return "{\"result\" : \"信件已發送，請至信箱查看\"}";
    }

    //驗證信件Token，跳轉密碼更改頁
    @GetMapping("/passwordReset")
    public String passwordReset(@RequestParam String token, Model model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByTokenAndExpiryDate(token, new Date());
        if (passwordResetToken == null) {
            System.out.println("找不到憑證:" + token);
            return "forgot_password_reset";
        }

        //重設密碼
        String newPassword = mailService.createUUID();
        System.out.println("newPassword:" + newPassword);
        User user = passwordResetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(null);
        passwordResetTokenService.createOrUpdateToken(passwordResetToken);

        model.addAttribute("reset", newPassword);
        return "forgot_password_reset";
    }

    //忘記密碼頁面
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgot_password";
    }
}
