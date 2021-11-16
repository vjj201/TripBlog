package com.java017.tripblog.controller;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserArticleController {

    @Autowired
    private final ArticleService articleService;
    private final UserService userService;
    private final RecommendService recommendService;
    private final CollectService collectService;
    private final ReportService reportService;


    public UserArticleController(ArticleService articleService, UserService userService, RecommendService recommendService, CollectService collectService, ReportService reportService) {
        this.articleService = articleService;
        this.userService = userService;
        this.recommendService = recommendService;
        this.collectService = collectService;
        this.reportService = reportService;
    }

    //庭妤   前端get已經推薦
    @ResponseBody
    @GetMapping("/alreadyTravelEatButtoned")
    public ArrayList<Recommend> alreadyButtoned (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        ArrayList<Recommend> messageList;
        messageList = recommendService.findByuserRecommendId(userId);

        System.out.println(" 已推薦messageList="+ messageList);

        return messageList;
    }

    @ResponseBody
    @GetMapping("/alreadyTravelEatButtonedForCollect")
    public ArrayList<Collect> alreadyButtonedForCollect (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        System.out.println("controller裡面的userID" + userId);
        ArrayList<Collect> messageList;
        messageList = collectService.findByuserCollectId(userId);
        System.out.println("controller裡面的messageList" + messageList);
        System.out.println(" 已收藏messageList="+ messageList);
        return messageList;
    }

    @ResponseBody
    @GetMapping("/alreadyTravelEatButtonedForReport")
    public ArrayList<Report> alreadyButtonedForReport (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        System.out.println("controller裡面的userID" + userId);
        ArrayList<Report> messageList;
        messageList = reportService.findByuserReportId(userId);
        System.out.println("controller裡面的messageList" + messageList);
        System.out.println(" 已收藏messageList="+ messageList);
        return messageList;
    }






//庭妤: 文章自動生成_輸入搜尋吧查詢並送出第一頁
    @ResponseBody
    @GetMapping("/firstSearchOfPageEatTravel")
    public List<Article> firstSearchOfPage(@RequestParam String enterAddressName, @RequestParam String subject, @RequestParam int timeDirect) {
        System.out.println("搜尋吧-enterAddressName=" + enterAddressName);
        System.out.println("搜尋吧-subject=" + subject);

        List<Article> messageList;
        messageList = articleService.getPagedArticles(0, 5, enterAddressName, subject, timeDirect);
        System.out.println("搜尋吧-順序timeDirect=" + timeDirect);
        System.out.println("搜尋吧-messageList=" + messageList);
//        User user = userService.getCurrentUser();
//        model.addAttribute("user",user);
        return messageList;
    }

    // 點擊換頁按鈕並換頁
    @ResponseBody
    @GetMapping("/changeSearchOfPageEatTravel")
    public List<Article> changeSearchOfPage(@RequestParam String enterAddressName,@RequestParam String subject, @RequestParam int page, @RequestParam int timeDirect) {
        System.out.println("點擊換頁按鈕並換頁的enterAddressName=" + enterAddressName);
        System.out.println("點擊換頁按鈕並換頁的subject=" + subject);

        List<Article> messageList;
        messageList = articleService.getPagedArticles(page, 5, enterAddressName,subject, timeDirect);
        System.out.println("點擊換頁按鈕並換頁的-順序timeDirect=" + timeDirect);
        System.out.println("點擊換頁按鈕並換頁的messageList=" + messageList);
        return messageList;

    }

    @ResponseBody
    @GetMapping("/myChangeSearchOfPageEatTravel")
    public List<Article> myChangeSearchOfPage(HttpSession session, @RequestParam String subject, @RequestParam Integer page, @RequestParam Integer timeDirect){
        System.out.println("點擊換頁按鈕並換頁的subject=" + subject);
        User user = (User) session.getAttribute("user");
        user = userService.findUserById(user.getId());
        Long userId = user.getId();
        System.out.println("myChangeSearchOfPageEatTravel的" + userId);
        List<Article> messageList;
        messageList = articleService.getMyPagedArticles(page,5,userId, subject,timeDirect);
        System.out.println("點擊換頁按鈕並換頁的-順序my_timeDirect=" + timeDirect);
        System.out.println("點擊換頁按鈕並換頁的my_messageList=" + messageList);
        return messageList;
    }

    //庭妤:  文章自動生成_自動生成換頁按鈕
    @ResponseBody
    @GetMapping("/newPageButtonEatTravel")
    public Integer newChangePageButton(@RequestParam String enterAddressName, @RequestParam String subject) {
        System.out.println("自動生成換頁按鈕enterAddressName=" + enterAddressName);
        System.out.println("自動生成換頁按鈕subject=" + subject);
        ArrayList<Article> list;
        list = articleService.findByEnterAddressNameLikeAndSubjectCategory(enterAddressName, subject);
        System.out.println("分頁按鈕-撈出的文章=" + list);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize / 5);
        return pageMount;
    }







}
