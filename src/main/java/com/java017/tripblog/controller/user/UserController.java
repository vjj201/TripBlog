package com.java017.tripblog.controller.user;

import com.java017.tripblog.entity.Intro;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.IntroService;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Base64;
import java.util.UUID;


/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:11
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final IntroService introService;

    @Autowired
    public UserController(UserService userService, IntroService introService) {
        this.userService = userService;
        this.introService = introService;
    }

    //判斷記住帳號
    private boolean isRememberMeUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        //判斷當前使用者是否是通過
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

        //跳轉登入畫面
    @GetMapping("/loginPage")
    public String loginPage(HttpSession session) {
        if(isRememberMeUser()) {
            afterLogin(session);
            return "redirect:/";
        }
        return "user/loginPage";
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

        User profile = userService.findUserById(user.getId());
        Intro intro = profile.getIntro();
        model.addAttribute("intro", intro);

        //保留必要資料
        profile.setPassword(null);
        profile.setId(null);
        profile.setIntro(null);

        model.addAttribute("profile", profile);
        return "user/my_profile";
    }

    //跳轉會員自介
    @GetMapping("/space")
    public String spacePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Intro intro = introService.showIntroData(user.getId());

        //自我介紹內容空白、換行處理
        if(intro.getIntroContent() != null){
            String textarea = intro.getIntroContent().replace("\n","<br>").replace("\r"," ");
            intro.setIntroContent(textarea);
        }
        model.addAttribute("intro", intro);

        return "/user/my_space";
    }

    //跳轉我的旅遊文章
    @GetMapping("/travel")
    public String travelPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Intro intro = userService.findUserById(user.getId()).getIntro();
        model.addAttribute("intro", intro);

        return "/user/my_article_travel";
    }

    //跳轉我的美食文章
    @GetMapping("/eat")
    public String eatPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Intro intro = userService.findUserById(user.getId()).getIntro();
        model.addAttribute("intro", intro);

        return "/user/my_article_eat";
    }

    //跳轉收藏頁
    @GetMapping("/collection")
    public String collectionPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Intro intro = userService.findUserById(user.getId()).getIntro();
        model.addAttribute("intro", intro);

        return "/user/my_collection";
    }

    //跳轉通知頁
    @GetMapping("/notify")
    public String notifyPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Intro intro = userService.findUserById(user.getId()).getIntro();
        model.addAttribute("intro", intro);

        return "/user/my_notify";
    }

    //登入後判斷狀態
    @GetMapping("/afterLogin")
    public String afterLogin(HttpSession session) {
        User user = userService.getCurrentUser();
        System.out.println(user);

        User userSession = new User();
        userSession.setId(user.getId());
        userSession.setNickname(user.getNickname());

        //是否完成信箱驗證
        if(user.isMailVerified()) {
            session.setAttribute("user", userSession);
            return "redirect:/";
        } else {
            userSession.setEmail(user.getEmail());
            session.setAttribute("signup", userSession);
            return "redirect:user/signup_success";
        }

    }

    //確認會員帳號是否重複
    @ResponseBody
    @GetMapping("/accountCheck")
    public boolean findUserByAccount(@RequestParam String username) {
        User user = userService.findUserByUsername(username);
        return user != null;
    }

    //註冊會員
    @ResponseBody
    @PostMapping("/signup")
    public boolean signup(@RequestBody User user, HttpSession session) {

        System.out.println("會員註冊 : " + user);

        //避免帳號重複
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return false;
        }

        boolean result = false;

        try {
            result = userService.createUser(user);
            System.out.println("註冊成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        User userSession = new User();
        userSession.setId(user.getId());
        userSession.setNickname(user.getNickname());
        userSession.setEmail(user.getEmail());
        session.setAttribute("signup", userSession);

        return result;
    }

    //查詢會員資料
    @ResponseBody
    @GetMapping("/getUser")
    public User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        user = userService.findUserById(user.getId());
        user.setPassword(null);
        user.setId(null);
        user.setIntro(null);

        return user;
    }

    //更新會員資料
    @ResponseBody
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody User userUpdate, HttpSession session) {

        User user = (User) session.getAttribute("user");
        user = userService.findUserById(user.getId());

        user.setName(userUpdate.getName());
        user.setNickname(userUpdate.getNickname());
        user.setBirthday(userUpdate.getBirthday());
        user.setEmail(userUpdate.getEmail());
        user.setPhone(userUpdate.getPhone());

        return userService.updateUser(user) != null;
    }

    //更新會員自我介紹頁面
    @ResponseBody
    @PostMapping("/updateIntro")
    public boolean updateIntro(@RequestBody Intro introUpdate, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Intro intro = introService.showIntroData(user.getId());

        //判斷自我介紹標題欄位是否為空
        if (!"".equals(introUpdate.getIntroTitle())) {
            intro.setIntroTitle(introUpdate.getIntroTitle());
        }
        //判斷自我介紹內容欄位是否為空
        if (!"".equals(introUpdate.getIntroContent())) {
            intro.setIntroContent(introUpdate.getIntroContent());
        }
        return introService.editIntro(intro) != null;
    }

    //更新會員自我介紹Link
    @ResponseBody
    @PostMapping("/updateIntroLink")
    public boolean updateIntroLink(@RequestBody Intro introUpdate, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Intro intro = introService.showIntroData(user.getId());

        //判斷fb連結欄位是否為空
        if (!"".equals(introUpdate.getFbLink())) {
            intro.setFbLink(introUpdate.getFbLink());
        }
        //判斷ig連結欄位是否為空
        if (!"".equals(introUpdate.getIgLink())) {
            intro.setIgLink(introUpdate.getIgLink());
        }
        //判斷ty連結欄位是否為空
        if (!"".equals(introUpdate.getYtLink())) {
            intro.setYtLink(introUpdate.getYtLink());
        }
        //判斷mail連結欄位是否為空
        if (!"".equals(introUpdate.getEmailLink())) {
            intro.setEmailLink(introUpdate.getEmailLink());
        }
        return introService.editIntro(intro) != null;
    }

    //更新會員MySpace頁面背景圖
    @ResponseBody
    @PostMapping("/updateIntroBanner")
    public boolean updateIntroBanner(@RequestBody String fileB64, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Intro intro = introService.showIntroData(user.getId());

        intro.setBannerPic(fileB64.split(",")[1]);
        intro.setBannerContent(fileB64.split(",")[0]);

//        //base64 to Blob
//        byte[] decodedByte = Base64.getDecoder().decode(fileB64.split(",")[1]);
//
//        String fileDirec =
//                "/Users/leepeishan/TripBlog/src/main/resources/static/images/imgTest/"
//                        + user.getId() + "/IntroBanner";
//        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
//        System.out.println(fileDirec);
//        File dir = new File(fileDirec);
//        if(!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        File file = new File(fileDirec, fileName);
//
//        // Write the image bytes to file.
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            file.createNewFile();
//            int count = decodedByte.length;
//            bos.write(decodedByte, 0, count);
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        String filePath = fileDirec.split("/resources/static")[1] + "/" + fileName;
//        System.out.println(filePath);
//        intro.setBannerPic(filePath);

        return introService.editIntro(intro) != null;
    }
}
