package com.java017.tripblog.controller;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class MapSearchController {

    @Autowired
    private final ArticleRepository articleRepository;

    public MapSearchController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @ResponseBody
    @GetMapping("/findByAddress")
    public ArrayList<Article> findByAddress(@RequestParam String enterAddressName) {

     //   Article article = articleRepository.findByEnterAddress(enterAddress);
        ArrayList<Article> list;
        list = articleRepository.findByEnterAddressName(enterAddressName);
        System.out.println(list);
        return list;
    };
}

