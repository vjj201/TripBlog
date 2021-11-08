package com.java017.tripblog.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @ManyToOne
    @JoinColumn(name = "article_recommend_id")
    private Article articlesRecommendId;

    @ManyToOne
    @JoinColumn(name = "User_recommend_id")
    private User userRecommendId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticlesRecommendId() {
        return articlesRecommendId;
    }

    public void setArticlesRecommendId(Article articlesRecommendId) {
        this.articlesRecommendId = articlesRecommendId;
    }

    public User getUserRecommendId() {
        return userRecommendId;
    }

    public void setUserRecommendId(User userRecommendId) {
        this.userRecommendId = userRecommendId;
    }
}
