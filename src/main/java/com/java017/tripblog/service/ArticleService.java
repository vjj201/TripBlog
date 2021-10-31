package com.java017.tripblog.service;

import com.java017.tripblog.entity.Article;


import java.util.ArrayList;
import java.util.List;

public interface ArticleService {

    String insertArticle(Article article);

    ArrayList<Article> findByEnterAddressNameLike(String enterAddressName);

    //map_search換頁
    List<Article> getPagedArticles(int page, int size, String address);

    //預設(無篩選)_user_eat&travel換頁
    List<Article> getUserEatTravelPagedArticles(int page, int size,String subject);

    ArrayList<Article> findBySubjectCategory(String subject);

    Article findByArticleTitle(String articleTitle);

}
