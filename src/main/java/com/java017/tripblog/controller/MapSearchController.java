package com.java017.tripblog.controller;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.repository.RecommendRepository;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MapSearchController {

    @Autowired
    private final ArticleService articleService;
    private final UserService userService;

    public MapSearchController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }


    @ResponseBody
    @GetMapping("/findByAddress")
    public ArrayList<Article> findByAddress(@RequestParam String enterAddressName) {
        //   Article article = articleRepository.findByEnterAddress(enterAddress);
        ArrayList<Article> list;
        list = articleService.findByEnterAddressNameLike(enterAddressName);
        System.out.println(list);
        return list;
    }

    ;
//----------------------------------------------------------------------------------

    //輸入搜尋吧查詢並送出第一頁
    @ResponseBody
    @GetMapping("/firstSearchOfPage")
    public List<Article> firstSearchOfPage(@RequestParam String enterAddressName, @RequestParam String subject, @RequestParam int timeDirect) {

        List<Article> messageList;
        messageList = articleService.getPagedArticles(0, 5, enterAddressName, subject, timeDirect);
        System.out.println("timeDirect=" + timeDirect);
        System.out.println("messageList=" + messageList);
        return messageList;
    }

    //自動生成換頁按鈕
    @ResponseBody
    @GetMapping("/newPageButton")
    public Integer newChangePageButton(@RequestParam String enterAddressName, @RequestParam String subject) {
        System.out.println("分頁按鈕AAA" + enterAddressName);
        System.out.println("分頁按鈕BBB" + subject);
        ArrayList<Article> list;
        list = articleService.findByEnterAddressNameLikeAndSubjectCategory(enterAddressName, subject);
        System.out.println("分頁按鈕" + list);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize / 5);
        return pageMount;
    }

    ;

    // 點擊換頁按鈕並換頁
    @ResponseBody
    @GetMapping("/changeSearchOfPage")
    public List<Article> changeSearchOfPage(@RequestParam String enterAddress, @RequestParam String subject, @RequestParam int page, @RequestParam int timeDirect) {

        List<Article> messageList;
        messageList = articleService.getPagedArticles(page, 5, enterAddress, subject, timeDirect);
        System.out.println("點擊換頁按鈕並換頁的messageList" + messageList);
        return messageList;

    }
    @Autowired
    RecommendRepository recommendRepository;

    @ResponseBody
    @GetMapping("/findByArticleTitle")
    public Article findByArticleTitle(@RequestParam String articleTitle) {

        Article result = articleService.findByArticleTitle(articleTitle);
        return result;
    }

//    //跳轉單一文章頁面
//    @GetMapping("/articleForone")
//    public String signupOkPage() {
//        return "/article.html";
//    }

    @ResponseBody
    @PostMapping("/forRecommend")
    public String updateRecommend(@RequestParam String articleTitle) {
        System.out.println("有道forRecommend 控制器");
        articleService.updateRecommend(articleTitle);
        return "推薦成功";
    }

    @ResponseBody
    @PostMapping("/forCollect")
    public String updateCollect(@RequestParam String articleTitle) {
        System.out.println("有道Collect 控制器");
        articleService.updateCollect(articleTitle);
        return "收藏成功";
    }

    @ResponseBody
    @PostMapping("/forReport")
    public String updateReport(@RequestParam String articleTitle) {
        System.out.println("有道Collect 控制器");
        articleService.updateReport(articleTitle);
        return "檢舉成功";
    }

    //渲染文章內容
    @GetMapping("/article/{articleTitle}")
    public String articlePage(Model model, @PathVariable String articleTitle) {
        Article showArticle = articleService.findByArticleTitle(articleTitle);
        model.addAttribute("showArticle",showArticle);
        User showUser = userService.getCurrentUser();
        model.addAttribute("showUser",showUser);
        return "article"; }




}

