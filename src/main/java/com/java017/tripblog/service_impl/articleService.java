package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.service.Iarticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class articleService implements Iarticle {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public String insertArticle(Article article) {

        Article article1 = new Article();
        article1 = article;
    //    article1.setFree_tag() = ;

        articleRepository.save(article1);

        return "新增成功";
    }
}
