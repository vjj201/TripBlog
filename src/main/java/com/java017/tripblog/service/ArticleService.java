package com.java017.tripblog.service;

import com.java017.tripblog.entity.Article;


import java.util.ArrayList;
import java.util.List;

public interface ArticleService {

    String insertArticle(Article article);

    ArrayList<Article> findByEnterAddressNameLike(String enterAddressName);

    List<Article> getPagedArticles(int page, int size, String address);

    Article findByArticleTitle(String articleTitle);

}
