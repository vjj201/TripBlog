package com.java017.tripblog.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "article_Report_id")
    private Article articlesReportId;

    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Report_id")
    private User userReportId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticlesReportId() {
        return articlesReportId;
    }

    public void setArticlesReportId(Article articlesReportId) {
        this.articlesReportId = articlesReportId;
    }

    public User getUserReportId() {
        return userReportId;
    }

    public void setUserReportId(User userReportId) {
        this.userReportId = userReportId;
    }
}
