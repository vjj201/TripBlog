package com.example.tripblog.service;

import com.example.tripblog.entity.User;
import com.example.tripblog.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.SecureRandom;

/**
 * @author YuCheng
 * @date 2021/10/12 - 上午 02:05
 */

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailUtils mailUtils;
    private final String SIGNUP = "TripBlog SignUp";

    @Override
    public void sendSignupMail(HttpSession session) {
        User user = (User) session.getAttribute("signup");
        String code = generateVerificationCode(6);
        session.setAttribute("SignupCode", code);
        String content = mailUtils.createMailVerificationContent(user.getNickname(), code);
        mailUtils.sendHtmlMail(user.getEmail(), SIGNUP, content);
    }

    @Override
    public boolean verifySignupCode(String code, HttpSession session) {
        String signupCode = (String)session.getAttribute("SignupCode");

        if(signupCode == null) {
            System.out.println("驗證失效");
            return false;
        }

        if(code.equals(signupCode)) {
            System.out.println("驗證碼正確");
            session.removeAttribute("SignupCode");
            return true;
        }
        System.out.println("驗證碼錯誤");
        return false;
    }

    @Override
    public String generateVerificationCode(int length) {
        //安全隨機數
        SecureRandom secureRandom = new SecureRandom();
        //驗證碼陣列長度
        char[] chars = new char[length];
        //驗證碼內容
        String symbol = "0123456789abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < chars.length; i++) {
            //隨機獲取值
            chars[i] = symbol.charAt(secureRandom.nextInt(symbol.length()));
        }
        //轉換成字串
        String code = String.valueOf(chars);
        System.out.println("驗證碼:" + code);
        return code;
    }
}
