package com.java017.tripblog.controller.article;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MapSearchController {


    @Autowired
    private final ArticleService articleService;
    private final UserService userService;


    public MapSearchController(ArticleService articleService, UserService userService, RecommendService recommendService, CollectService collectService, ReportService reportService) {
        this.articleService = articleService;
        this.userService = userService;
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

