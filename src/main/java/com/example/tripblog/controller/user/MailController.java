package com.example.tripblog.controller.user;

import com.example.tripblog.entity.User;
import com.example.tripblog.service.MailService;
import com.example.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author YuCheng
 * @date 2021/10/12 - 上午 02:03
 */

@RestController
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;


    //發送信件
    @GetMapping("/sendSignupMail")
    public void sendSignUpMail(HttpSession session) {
        mailService.sendSignupMail(session);
        System.out.println("發送信件");
    }

    //驗證信件
    @PostMapping("/checkSignupMail")
    public boolean checkSignUpMail(@RequestParam String code, HttpSession session) {
        System.out.println("使用者輸入的驗證:" + code);
        boolean result =  mailService.verifySignupCode(code, session);

        if(result) {
            System.out.println("驗證成功");
            User user = (User)session.getAttribute("signup");


            User updateUser = userService.showUserData(user.getId());
            updateUser.setMailVerified(true);
            userService.editorUserData(updateUser);


            session.removeAttribute("signup");
            return true;
        }else {
            System.out.println("驗證失敗");
            return false;
        }
    }
}
