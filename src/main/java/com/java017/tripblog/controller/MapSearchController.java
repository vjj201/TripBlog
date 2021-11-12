package com.java017.tripblog.controller;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.repository.RecommendRepository;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service.RecommendService;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MapSearchController {

    @Autowired
    private final ArticleService articleService;
    private final UserService userService;
    private final RecommendService recommendService;

    public MapSearchController(ArticleService articleService, UserService userService, RecommendService recommendService) {
        this.articleService = articleService;
        this.userService = userService;
        this.recommendService = recommendService;
    }

    //庭妤   前端get已經收藏&推薦&檢舉
//    @ResponseBody
//    @GetMapping("/alreadyButtoned")
//    public ArrayList<Recommend> alreadyButtoned (HttpSession session){
//        User user = (User) session.getAttribute("user");
//        User userId;
//        userId = userService.findUserById(user.getId());  //userId
//        System.out.println("userId="+userId);
//
//
//        ArrayList<Recommend> messageList;
//        messageList = recommendService.findByuserRecommendId(userId);
//        System.out.println(" 已收藏messageList="+ messageList);
//
//            return messageList;
//    }






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
        System.out.println("有進入到/firstSearchOfPage");
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
        System.out.println("有進入到/newPageButton");
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
    public List<Article> changeSearchOfPage(@RequestParam String enterAddressName, @RequestParam String subject, @RequestParam int page, @RequestParam int timeDirect) {

        List<Article> messageList;
        messageList = articleService.getPagedArticles(page, 5, enterAddressName, subject, timeDirect);
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



    // 使用者按讚 by 大方 start

    @ResponseBody
    @PostMapping("/forRecommend")
    public String updateRecommend (HttpSession session, @RequestParam String articleTitle) {
        System.out.println("有到forRecommend 控制器");
        articleService.updateRecommend(articleTitle);
//        return "推薦成功";

//        System.out.println("參數：articleTitle" + articleTitle);

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

//        System.out.println("是不是從這裡開始有問題");
        if (recommend.getUserRecommendId() != null && recommend.getArticlesRecommendId() != null) {
//            System.out.println("4你？");
//            System.out.println(recommend);
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

    @ResponseBody
    @GetMapping("/randomArticle")
    public List<Article> articles(){
        ArrayList<Article> result;
        result = articleService.findByRandomArticle();
        System.out.println("控制器" + result);
        return result;
    }


}

