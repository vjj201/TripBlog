package com.java017.tripblog.util;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Collect;

import java.util.ArrayList;

public class ChangleToArticle {

    public ArrayList<Article> CollectChangleArticle(ArrayList<Collect> collects){
        ArrayList<Article> articles = new ArrayList<>();
        for ( Collect collect:collects) {
            Article article = new Article();
            article.setArticleId(collect.getArticlesCollectId().getArticleId());
            article.setSubjectCategory(collect.getArticlesCollectId().getSubjectCategory());
            article.setArticleTitle(collect.getArticlesCollectId().getArticleTitle());
            article.setSaveImgPath(collect.getArticlesCollectId().getSaveImgPath());
            article.setEnterAddressName(collect.getArticlesCollectId().getEnterAddressName());
            article.setEnterAddressLat(collect.getArticlesCollectId().getEnterAddressLat());
            article.setEnterAddressLng(collect.getArticlesCollectId().getEnterAddressLng());
            article.setTextEditor(collect.getArticlesCollectId().getTextEditor());
//            article.setFreeTag(collect.getArticlesCollectId().getFreeTag());
            article.setCreateTime(collect.getArticlesCollectId().getCreateTime());
            article.setCollect(collect.getArticlesCollectId().getCollect());
            article.setUserId(collect.getArticlesCollectId().getUserId());
            article.setRecommend(collect.getArticlesCollectId().getRecommend());
            article.setReport(collect.getArticlesCollectId().getReport());
            article.setSelectRegion(collect.getArticlesCollectId().getSelectRegion());
            articles.add(article);
        }
        return articles;

    }
}
