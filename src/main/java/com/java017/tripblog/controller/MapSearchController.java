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
public class MapSearchController {

    @Autowired
    private final ArticleService articleService;

    public MapSearchController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @ResponseBody
    @GetMapping("/findByAddress")
    public ArrayList<Article> findByAddress(@RequestParam String enterAddressName) {
     //   Article article = articleRepository.findByEnterAddress(enterAddress);
        ArrayList<Article> list;
        list = articleService.findByEnterAddressNameLike(enterAddressName);
        System.out.println(list);
        return list;
    };
//----------------------------------------------------------------------------------
    //輸入搜尋吧查詢並送出第一頁
    @ResponseBody
    @GetMapping("/firstSearchOfPage")
    public List<Article> firstSearchOfPage(@RequestParam String enterAddressName){

        List<Article> messageList;
        messageList = articleService.getPagedArticles(0,5,enterAddressName);
        System.out.println("messageList"+messageList);
        return messageList;
    }

    //自動生成換頁按鈕
    @ResponseBody
    @GetMapping("/newPageButton")
    public Integer findByAddressPage(@RequestParam String enterAddressName) {

        //   Article article = articleRepository.findByEnterAddress(enterAddress);
        ArrayList<Article> list;
        list = articleService.findByEnterAddressNameLike(enterAddressName);
        System.out.println("分頁按鈕"+list);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize/5);
        return pageMount;
    };

    @ResponseBody
    @GetMapping("/changeSearchOfPage")
    public List<Article> changeSearchOfPage(@RequestParam String enterAddress,@RequestParam int page){

        List<Article> messageList;
        messageList = articleService.getPagedArticles(page,5,enterAddress);
        System.out.println(messageList);
        return messageList;

    }








}

