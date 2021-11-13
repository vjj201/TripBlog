package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.PasswordResetToken;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.MailService;
import com.java017.tripblog.service.PasswordResetTokenService;
import com.java017.tripblog.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author YuCheng
 * @date 2021/10/12 - 上午 02:05
 */

@Service
public class MailServiceImpl implements MailService {

    private final String SIGNUP = "TripBlog SignUp";
    private final String RestPassword = "TripBlog RestPassword";
    private final String SYMBOL = "0123456789";
    private final MailUtils mailUtils;

    @Autowired
    public MailServiceImpl(MailUtils mailUtils, PasswordResetTokenService passwordResetTokenService) {
        this.mailUtils = mailUtils;
    }

    //寄送驗證信
    @Override
    public void sendSignupMail(HttpSession session) {
        User user = (User) session.getAttribute("user");
        String code = generateVerificationCode(6);
        session.setAttribute("SignupCode", code);
        String content = mailUtils.createMailVerificationContent(user.getNickname(), code);
        mailUtils.sendHtmlMail(user.getEmail(), SIGNUP, content);
    }

    //驗證確認
    @Override
    public boolean verifySignupCode(String code, HttpSession session) {
        String signupCode = (String) session.getAttribute("SignupCode");

        if (signupCode == null) {
            System.out.println("驗證失效");
            return false;
        }

        if (code.equals(signupCode)) {
            System.out.println("驗證碼正確");
            session.removeAttribute("SignupCode");

            return true;
        }
        System.out.println("驗證碼錯誤");
        return false;
    }

    //產生驗證碼
    @Override
    public String generateVerificationCode(int length) {
        //安全隨機數
        SecureRandom secureRandom = new SecureRandom();
        //驗證碼陣列長度
        char[] chars = new char[length];
        //驗證碼內容
        for (int i = 0; i < chars.length; i++) {
            //隨機獲取值
            chars[i] = SYMBOL.charAt(secureRandom.nextInt(SYMBOL.length()));
        }
        //轉換成字串
        String code = String.valueOf(chars);
        System.out.println("驗證碼:" + code);
        return code;
    }

    //寄送重設密碼信
    @Override
    public void sendPasswordResetMail(String email, String nickname,String link) {
        //創建信件
        String content = mailUtils.createMailResetPasswordContent(nickname, link);
        mailUtils.sendHtmlMail(email, RestPassword, content);
    }

    //產生UUID Token link
    @Override
    public String createUUID() {
        return UUID.randomUUID().toString();
    }

    //產生UUID Token link
    @Override
    public String generateTokenLink(HttpServletRequest request, String UUID) {
        return request.getRequestURI().replace(request.getServletPath(), "")
                + "/passwordReset?token="
                + UUID;
    }
}
