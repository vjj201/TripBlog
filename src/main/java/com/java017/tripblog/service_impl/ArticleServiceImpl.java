package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public String insertArticle(Article article) {

        Article article1 = new Article();
        article1 = article;
    //    article1.setFree_tag() = ;

        articleRepository.save(article1);

        return "新增成功";
    }
}
