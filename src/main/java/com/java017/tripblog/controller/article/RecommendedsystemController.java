package com.java017.tripblog.controller.article;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller
public class RecommendedsystemController {

    private final ArticleService articleService;
    private final UserService userService;
    private final RecommendService recommendService;
    private final CollectService collectService;
    private final ReportService reportService;

    public RecommendedsystemController(ArticleService articleService, UserService userService, RecommendService recommendService, CollectService collectService, ReportService reportService) {
        this.articleService = articleService;
        this.userService = userService;
        this.recommendService = recommendService;
        this.collectService = collectService;
        this.reportService = reportService;
    }

    @ResponseBody
    @PostMapping("/forRecommend")
    public String updateRecommend (HttpSession session, @RequestParam String articleTitle) {
        System.out.println("有到forRecommend 控制器");
        articleService.updateRecommend(articleTitle);
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        System.out.println("使用者是誰 (user)" + user);  //取得userBean
        System.out.println("使用者是誰 (userId)" + userId);

        Recommend recommend = new Recommend();
        recommend.setUserRecommendId(userId);    //存入userId
        System.out.println("推薦者 (存入userId)： " + recommend);

        Article articleId = articleService.findByArticleTitle(articleTitle); //用文章標題找文章ID
        recommend   .setArticlesRecommendId(articleId);   //存入articleId
        System.out.println("文章Id (存入articleId): " + articleId);
        System.out.println("推薦者 (recommend)：" + recommend);

        if (recommend.getUserRecommendId() != null && recommend.getArticlesRecommendId() != null) {

            recommendService.updateUserRecommend(recommend);

            System.out.println("成功往service傳");
            System.out.println(recommend);
        } else {
            System.out.println("使用者沒登入or文章id沒抓到");
            return null;
        }
        return "推薦成功";
    }

    // 使用者按讚 by 大方 end


    @ResponseBody
    @PostMapping("/forCollect")
    public String updateCollect(@RequestParam String articleTitle, HttpSession session) {
        System.out.println("有到forRecommend 控制器");
        articleService.updateCollect(articleTitle);
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        System.out.println("使用者是誰 (user)" + user);  //取得userBean
        System.out.println("使用者是誰 (userId)" + userId);
        Collect collect = new Collect();
        collect.setUserCollectId(userId);    //存入userId
        System.out.println("推薦者 (存入userId)： " + collect);

        Article articleId = articleService.findByArticleTitle(articleTitle); //用文章標題找文章ID
        collect.setArticlesCollectId(articleId);   //存入articleId
        System.out.println("文章Id (存入articleId): " + articleId);
        System.out.println("推薦者 (recommend)：" + collect);

//        System.out.println("是不是從這裡開始有問題");
        if (collect.getUserCollectId() != null && collect.getArticlesCollectId() != null) {
//            System.out.println("4你？");
            System.out.println(collect.getArticlesCollectId());
            collectService.updateUserCollect(collect);
            System.out.println("成功往service傳");
            System.out.println(collect);
        } else {
            System.out.println("使用者沒登入or文章id沒抓到");
            return null;
        }
        return "收藏";
    }

    @ResponseBody
    @PostMapping("/forReport")
    public String updateReport(@RequestParam String articleTitle,HttpSession session) {
        System.out.println("有到forReport 控制器");
        articleService.updateReport(articleTitle);
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
        System.out.println("使用者是誰 (user)" + user);  //取得userBean
        System.out.println("使用者是誰 (userId)" + userId);
        Report report = new Report();
        report.setUserReportId(userId);    //存入userId
        System.out.println("推薦者 (存入userId)： " + report);

        Article articleId = articleService.findByArticleTitle(articleTitle); //用文章標題找文章ID
        report.setArticlesReportId(articleId);   //存入articleId
        System.out.println("文章Id (存入articleId): " + articleId);
        System.out.println("推薦者 (recommend)：" + report);

//        System.out.println("是不是從這裡開始有問題");
        if (report.getUserReportId() != null && report.getArticlesReportId() != null) {
//            System.out.println("4你？");
            System.out.println(report.getArticlesReportId());
            reportService.updateUserReport(report);
            System.out.println("成功往service傳");
            System.out.println(report);
        } else {
            System.out.println("使用者沒登入or文章id沒抓到");
            return null;
        }
        return "檢舉";
    }
}
