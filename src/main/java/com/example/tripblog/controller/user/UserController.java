package com.example.tripblog.controller.user;

import com.example.tripblog.entity.User;
import com.example.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:11
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //跳轉登入畫面
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    //跳轉註冊畫面
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "user/signup";
    }

    //跳轉註冊成功畫面
    @GetMapping("/signup-success")
    public String signupOkPage() {
        return "user/signup-success";
    }

    //跳轉會員資料頁
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        User profile = userService.showUserData(user.getId());
        //密碼保留末三位
        String password = profile.getPassword();
        int size = password.length();

        String substring = password.substring(size - 3);
        String prefix = "";
        for (int i = 0; i < size - 3; i++) {
            prefix += "*";
        }
        password = prefix + substring;

        //保留必要資料
        profile.setPassword(password);
        profile.setId(null);
        profile.setIv(null);

        model.addAttribute("profile", profile);
        return "user/profile";
    }

    //驗證會員登入
    @ResponseBody
    @PostMapping("/login")
    public int signup(@RequestBody User user,
                      HttpSession session
    ) {

        user = userService.checkUser(user.getAccount(), user.getPassword());

        System.out.println("登入資料:" + user);

        if (user != null) {
            System.out.println("登入成功");
            //設置會話資料
            User userSession = new User();
            userSession.setId(user.getId());
            userSession.setNickname(user.getNickname());

            session.setAttribute("user", userSession);
            return 1;

        } else {
            System.out.println("登入失敗");
            return 0;
        }

    }

    //確認會員帳號是否重複
    @ResponseBody
    @GetMapping("/accountCheck")
    public boolean findUserByAccount(@RequestParam String account) {
        User user = userService.showUserData(account);
        return user != null;
    }

    //帳號登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //會話移除
        session.removeAttribute("user");
        return "redirect:/";
    }

    //註冊會員
    @ResponseBody
    @PostMapping("/signup")
    public boolean signup(@RequestBody User user) {

        System.out.println(user);

        User userSaved = userService.showUserData(user.getAccount());

        if (userSaved != null || "".equals(user.getPassword()) || user.getPassword() == null) {
            return false;
        }

        try {
            userSaved = userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userSaved != null;
    }

}
