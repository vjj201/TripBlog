package com.java017.tripblog.controller;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserArticleController {

    @Autowired
    private final ArticleService articleService;

    public UserArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    //輸入搜尋吧查詢並送出第一頁
    @ResponseBody
    @GetMapping("/firstSearchOfPageEatTravel")
    public List<Article> firstSearchOfPage(@RequestParam String enterAddressName,@RequestParam String subject, @RequestParam int timeDirect) {
        System.out.println("搜尋吧-enterAddressName=" + enterAddressName);
        System.out.println("搜尋吧-subject=" + subject);

        List<Article> messageList;
        messageList = articleService.getPagedArticles(0, 5, enterAddressName, subject, timeDirect);
        System.out.println("搜尋吧-順序timeDirect=" + timeDirect);
        System.out.println("搜尋吧-messageList=" + messageList);
        return messageList;
    }


    //自動生成換頁按鈕
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













//    -----------------------------------------------------------------------------
    //預設(無篩選)_user_eat&travel換頁
    //輸入搜尋吧查詢並送出第一頁
//    @ResponseBody
//    @GetMapping("/firstSearchOfPageEatTravel")
//    public List<Article> firstSearchOfPageEatTravel(@RequestParam String subject){
//
//        List<Article> messageList;
//        messageList = articleService. getUserEatTravelPagedArticles(0,5,subject);
//        System.out.println("messageList"+messageList);
//        return messageList;
//    }


    //預設(無篩選)_user_eat&travel換頁
    //自動生成換頁按鈕
//    @ResponseBody
//    @GetMapping("/newPageButtonEatTravel")
//    public Integer newPageButtonEatTravel(@RequestParam String subject) {
//
//        //   Article article = articleRepository.findByEnterAddress(enterAddress);
//        ArrayList<Article> list;
//        list = articleService.findBySubjectCategory(subject);
//        System.out.println("eat分頁按鈕"+list);
//        double listSize = list.size();
//        int pageMount = (int) Math.ceil(listSize/5);
//        return pageMount;
//    }


    //預設(無篩選)_user_eat&travel換頁
    //換頁
//    @ResponseBody
//    @GetMapping("/changeSearchOfPageEatTravel")
//    public List<Article> changeSearchOfPageEatTravel(@RequestParam String subject,@RequestParam int page){
//
//        List<Article> messageList;
//        messageList = articleService. getUserEatTravelPagedArticles(page,5,subject);
//        System.out.println(messageList);
//        return messageList;
//
//    }


}
