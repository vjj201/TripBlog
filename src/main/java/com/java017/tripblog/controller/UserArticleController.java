package com.java017.tripblog.controller;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.repository.RecommendRepository;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service.RecommendService;
import com.java017.tripblog.service.UserService;
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


    public UserArticleController(ArticleService articleService, UserService userService, RecommendService recommendService) {
        this.articleService = articleService;
        this.userService = userService;
        this.recommendService = recommendService;
    }

    //庭妤   前端get已經收藏&推薦&檢舉
    @ResponseBody
    @GetMapping("/alreadyTravelEatButtoned")
    public ArrayList<Recommend> alreadyButtoned (HttpSession session){
        User user = (User) session.getAttribute("user");
        User userId;
        userId = userService.findUserById(user.getId());  //userId
//        user1.setId(userId.getId());
//        System.out.println("已收藏userId="+userId);
//        System.out.println("user1"+user1);

//        ArrayList<Article> A = articleService.findAll();
        ArrayList<Recommend> messageList;
        messageList = recommendService.findByuserRecommendId(userId);

        System.out.println(" 已收藏messageList="+ messageList);

        return messageList;
    }




//庭妤: 文章自動生成_輸入搜尋吧查詢並送出第一頁
    @ResponseBody
    @GetMapping("/firstSearchOfPageEatTravel")
    public List<Article> firstSearchOfPage(@RequestParam String enterAddressName,@RequestParam String subject, @RequestParam int timeDirect) {
        System.out.println("搜尋吧-enterAddressName=" + enterAddressName);
        System.out.println("搜尋吧-subject=" + subject);
        //------------------------
//     Recommend recommend = new Recommend();
//     Article article = new Article();
//     article.setArticleId(1);
//     recommend.setArticlesRecommendId(article);
//     String result =  recommend.getArticlesRecommendId();
//        System.out.println("幹你老師!!!!!!!!!!!!!!!!!"+ result);

        //------------------------
        List<Article> messageList;
        messageList = articleService.getPagedArticles(0, 5, enterAddressName, subject, timeDirect);
        System.out.println("搜尋吧-順序timeDirect=" + timeDirect);
        System.out.println("搜尋吧-messageList=" + messageList);
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

    //庭妤:    文章自動生成_點擊換頁按鈕並換頁
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




}
