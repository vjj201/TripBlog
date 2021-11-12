package com.java017.tripblog.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author YuCheng
 * @date 2021/10/12 - 上午 02:03
 */
public interface MailService {

    //寄送註冊驗證
    void sendSignupMail(HttpSession session);

    //驗證註冊碼
    boolean verifySignupCode(String code, HttpSession session);

    //生成驗證碼
    String generateVerificationCode(int length);

    //寄送重設密碼信
    void sendPasswordResetMail(String email, String nickname,String link);

    //產生UUID Token link
    String createUUID();

    //生成UUID Link
    String generateTokenLink(HttpServletRequest request, String UUID);
}
