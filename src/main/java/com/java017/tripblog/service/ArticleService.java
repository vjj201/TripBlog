package com.java017.tripblog.service;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import java.util.ArrayList;
import java.util.List;

public interface ArticleService {

    String insertArticle(Article article);

    ArrayList<Article> findByEnterAddressNameLike(String enterAddressName);


    Article findByArticleTitle(String articleTitle);


    String updateRecommend(String articleTitle);

    String updateCollect(String articleTitle);

    String updateReport(String articleTitle);

//庭妤:    文章首頁&文章換頁
    List<Article> getPagedArticles(int page, int size, String enterAddressName,String subject,int timeDirect);

//庭妤:      主題_物件陣列
    ArrayList<Article> findBySubjectCategory(String subject);

//庭妤:    文章換頁按鈕自動生成
    ArrayList<Article>findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName,String subject);

    ArrayList<Article> findByRandomArticle();

    ArrayList<Article> findUserById(User id);

    ArrayList<Article>findByUserIdForPage(User id);

    ArrayList<Article>findAll();
}
