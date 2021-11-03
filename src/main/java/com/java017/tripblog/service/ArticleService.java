package com.java017.tripblog.service;

import com.java017.tripblog.entity.Article;


import java.util.ArrayList;
import java.util.List;

public interface ArticleService {

    String insertArticle(Article article);

    ArrayList<Article> findByEnterAddressNameLike(String enterAddressName);


    Article findByArticleTitle(String articleTitle);


    String updateRecommend(String articleTitle);

    String updateCollect(String articleTitle);

    String updateReport(String articleTitle);

    //map_search換頁
    List<Article> getPagedArticles(int page, int size, String enterAddressName,String subject,int timeDirect);

    //預設(無篩選)_user_eat&travel換頁
    List<Article> getUserEatTravelPagedArticles(int page, int size,String subject);

    ArrayList<Article> findBySubjectCategory(String subject);

    ArrayList<Article>findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName,String subject);




}
