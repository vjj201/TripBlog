package com.java017.tripblog.controller;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserSpaceController {


    private final ArticleService articleService;
    private final RecommendService recommendService;
    private final CollectService collectService;
    private final ReportService reportService;
    private final UserService userService;

    @Autowired
    public UserSpaceController(ArticleService articleService, RecommendService recommendService, CollectService collectService, ReportService reportService, UserService userService) {
        this.articleService = articleService;
        this.recommendService = recommendService;
        this.collectService = collectService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/VisitorsFirstSearchOfPageEatTravel")
    public List<Article> myFirstSearchOfPage(@RequestParam Long userId, @RequestParam String subject, @RequestParam int timeDirect) {
        System.out.println("搜尋吧-subject=" + subject);
        List<Article> messagedList;
        messagedList = articleService.getMyPagedArticles(0, 5, userId, subject, timeDirect);
        return messagedList;
    }

    @ResponseBody
    @GetMapping("/VisitorsNewPageButtonForUser")
    public Integer newChangePageButton(@RequestParam Long userId, @RequestParam String subject) {
        System.out.println("自動生成換頁按鈕subject=" + subject);
        User user = new User();
        user.setId(userId);
        ArrayList<Article> list;
        list = articleService.findByUserIdAndSubjectCategoryForPage(user, subject);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize / 5);
        return pageMount;
    }

    @ResponseBody
    @GetMapping("/VisitorsChangeSearchOfPageEatTravel")
    public List<Article> myChangeSearchOfPage(@RequestParam Long userId, @RequestParam String subject, @RequestParam Integer page, @RequestParam Integer timeDirect){
        List<Article> messageList;
        messageList = articleService.getMyPagedArticles(page,5,userId, subject,timeDirect);
        System.out.println("點擊換頁按鈕並換頁的-順序my_timeDirect=" + timeDirect);
        System.out.println("點擊換頁按鈕並換頁的my_messageList=" + messageList);
        return messageList;
    }

    @ResponseBody
    @GetMapping(value = "/VisitorsAlreadyTravelEatButtoned/")
    public ArrayList<Recommend> alreadyButtoned (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        ArrayList<Recommend> messageList;;
        messageList = recommendService.findByUserRecommendId(userId);
        System.out.println("messageList" + messageList);
        return messageList;
    }

    @ResponseBody
    @GetMapping(value = "/VisitorsAlreadyTravelEatButtonedForCollect/")
    public ArrayList<Collect> alreadyButtonedForCollect (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());
        ArrayList<Collect> messageList;
        messageList = collectService.findByUserCollectId(userId);
        System.out.println("messageList" + messageList);
        return messageList;
    }

    @ResponseBody
    @GetMapping(value = "/VisitorsAlreadyTravelEatButtonedForReport/")
    public ArrayList<Report> alreadyButtonedForReport (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());
        ArrayList<Report> messageList;
        messageList = reportService.findByUserReportId(userId);
        System.out.println("messageList" + messageList);
        return messageList;
    }
//-------------------------------------------------------------------------------------------------------
    //第一頁產生文章
    @ResponseBody
    @GetMapping("/myFirstSearchOfPageEatTravelForCollect")
    public List<Collect> myFirstSearchOfPageForCollect(@RequestParam Long userId, @RequestParam String subject, @RequestParam int timeDirect) {
        User user;
        user = userService.findUserById(userId);
        List<Collect> messagedList;
        messagedList = articleService.findCollectByUser(0, 5, user, subject, timeDirect);
        return messagedList;
    }

    //我的會員收藏換頁數字
    @ResponseBody
    @GetMapping("/newPageButtonForCollect")
    public Integer newChangePageButtonForCollect(@RequestParam Long userId, @RequestParam String subject) {
        User user;
        user = userService.findUserById(userId);
        List<Collect> list;
        list = articleService.findCollectByUserCollectForPage(user, subject);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize / 5);
        return pageMount;
    }

    //我的會員收藏改換頁面
    @ResponseBody
    @GetMapping("/myChangeSearchOfPageEatTravelForCollect")
    public List<Collect> myChangeSearchOfPageFoeCollect(@RequestParam Long userId, @RequestParam String subject, @RequestParam Integer page, @RequestParam Integer timeDirect) {
        User user;
        user = userService.findUserById(userId);
        List<Collect> messageList;
        messageList = articleService.findCollectByUser(page, 5, user, subject, timeDirect);
        System.out.println("點擊換頁按鈕並換頁的my_messageList=" + messageList);
        return messageList;
    }
    //取消收藏
    @GetMapping("/deleteCollect/{articleId}")
    private String deleteMyCollect(@PathVariable Article articleId,HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("刪除文章標題：" + articleId);
        articleService.deleteMyCollect(user,articleId);
        System.out.println("執行刪除文章ok");
        return "redirect:/user/collection";
    }


}
