package com.java017.tripblog.service;

import com.java017.tripblog.entity.Article;

import java.util.ArrayList;

public interface ArticleService {

    String insertArticle(Article article);

    ArrayList<Article> findByEnterAddress(String address);

}
