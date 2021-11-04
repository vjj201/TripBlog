package com.java017.tripblog.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Collect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @ManyToOne
    @JoinColumn(name = "article_recommend_id")
    private Article articlesCollectId;

    @ManyToOne
    @JoinColumn(name = "User_recommend_id")
    private User userCollectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticlesCollectId() {
        return articlesCollectId;
    }

    public void setArticlesCollectId(Article articlesCollectId) {
        this.articlesCollectId = articlesCollectId;
    }

    public User getUserCollectId() {
        return userCollectId;
    }

    public void setUserCollectId(User userCollectId) {
        this.userCollectId = userCollectId;
    }
}
