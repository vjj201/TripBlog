package com.example.tripblog.controller.user;

import com.example.tripblog.entity.Intro;
import com.example.tripblog.entity.User;
import com.example.tripblog.service.IntroService;
import com.example.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:11
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IntroService introService;

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
        return "user/signup_success";
    }

    //跳轉會員資料頁
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        User profile = userService.showUserData(user.getId());


        //保留必要資料
        profile.setPassword(null);
        profile.setId(null);
        profile.setIv(null);

        model.addAttribute("profile", profile);
        return "user/my_profile";
    }

    //跳轉會員自介
    @GetMapping("/space")
    public String spacePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Long introId = user.getId();

        Intro intro = introService.showIntroData(introId);

        model.addAttribute("intro", intro);

        return "/user/my_space";
    }

    //跳轉我的旅遊文章
    @GetMapping("/travel")
    public String travelPage() {
        return "/user/my_article_travel";
    }

    //跳轉我的美食文章
    @GetMapping("/eat")
    public String eatPage() {
        return "/user/my_article_eat";
    }

    //跳轉收藏頁
    @GetMapping("/collection")
    public String collectionPage() {
        return "/user/my_collection";
    }

    //跳轉通知頁
    @GetMapping("/notify")
    public String notifyPage() {
        return "/user/my_notify";
    }

    //驗證會員登入
    @ResponseBody
    @PostMapping("/login")
    public int login(@RequestBody User user,
                      HttpSession session
    ) {

        user = userService.checkUser(user.getAccount(), user.getPassword());

        System.out.println("登入資料:" + user);

        if(user != null && !user.isMailVerified()) {
            User userSession = new User();
            userSession.setId(user.getId());
            userSession.setNickname(user.getNickname());
            userSession.setEmail(user.getEmail());
            session.setAttribute("signup", userSession);
            return -1;
        }

        if (user != null && user.isMailVerified()) {
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
    public boolean signup(@RequestBody User user, HttpSession session) {

        System.out.println(user);
        User newUser = new User();


        User userSaved = userService.showUserData(user.getAccount());


        if (userSaved != null || "".equals(user.getPassword()) || user.getPassword() == null) {
            return false;
        }

        try {
            userSaved = userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        newUser.setId(userSaved.getId());
        newUser.setNickname(userSaved.getNickname());
        newUser.setEmail(userSaved.getEmail());
        session.setAttribute("signup", newUser);

        System.out.println("註冊:" + newUser);

        return userSaved != null;
    }


    //查詢會員資料
    @ResponseBody
    @GetMapping("/getUser")
    public User getUser(HttpSession session) {
        User user = (User)session.getAttribute("user");
        user = userService.showUserData(user.getId());
        user.setPassword(null);
        user.setId(null);
        user.setIv(null);

        return user;
    }

    //更新會員資料
    @ResponseBody
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody User userUpdate, HttpSession session) {

        User user = (User) session.getAttribute("user");
        user = userService.showUserData(user.getId());

        user.setName(userUpdate.getName());
        user.setNickname(userUpdate.getNickname());
        user.setBirthday(userUpdate.getBirthday());
        user.setEmail(userUpdate.getEmail());
        user.setPhone((userUpdate.getPhone()));

        if(userService.editorUserData(user) != null) {
            return true;
        } else {
            return false;
        }
    }

    //更新會員自我介紹頁面
    @ResponseBody
    @PostMapping ("/updateIntro")
    public boolean updateIntro(@RequestBody Intro introUpdate, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Long introId = user.getId();

        Intro intro = introService.showIntroData(introId);

        intro.setIntroTitle(introUpdate.getIntroTitle());
        intro.setIntroContent(introUpdate.getIntroContent());

        if(introService.editIntro(intro) != null) {
            return true;
        } else {
            return false;
        }

    }
    //更新會員自我介紹Link
    @ResponseBody
    @PostMapping ("/updateIntroLink")
    public boolean updateIntroLink(@RequestBody Intro introUpdate, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Long introId = user.getId();

        Intro intro = introService.showIntroData(introId);

        intro.setFbLink(introUpdate.getFbLink());
        intro.setIgLink(introUpdate.getIgLink());
        intro.setYtLink(introUpdate.getYtLink());
        intro.setEmailLink(introUpdate.getEmailLink());

        if(introService.editIntro(intro) != null) {
            return true;
        } else {
            return false;
        }
    }
}
