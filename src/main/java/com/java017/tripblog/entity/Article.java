package com.java017.tripblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@JsonIgnoreProperties("userId")
@JsonSerialize
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "article")
public class Article{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ArticleId;

    String subjectCategory;

    String selectRegion;

    String enterAddressName;

    Double enterAddressLng;

    Double enterAddressLat;

    String articleTitle;

    String textEditor;

    String freeTag;

    Integer recommend = 0;

    Integer Report = 0;

    Integer collect = 0;

    String saveImgPath;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userId", referencedColumnName = "id")
     User userId;

//    @OneToMany(mappedBy="articlesRecommendId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//    private Set<Recommend>  recommendSet ;

    @OneToMany(mappedBy="articlesReportId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Report>  reportSet ;

    @OneToMany(mappedBy="articlesCollectId",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<Collect>  collectSet ;


    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createDate;

    @Temporal(TemporalType.TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createTime;
    public Article() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSaveImgPath() {
        return saveImgPath;
    }

    public void setSaveImgPath(String saveImgPath) {
        this.saveImgPath = saveImgPath;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

//    public Set<Recommend> getRecommendSet() {
//        return recommendSet;
//    }
//
//    public void setRecommendSet(Set<Recommend> recommendSet) {
//        this.recommendSet = recommendSet;
//    }

    public Set<com.java017.tripblog.entity.Report> getReportSet() {
        return reportSet;
    }

    public void setReportSet(Set<com.java017.tripblog.entity.Report> reportSet) {
        this.reportSet = reportSet;
    }

    public Set<Collect> getCollectSet() {
        return collectSet;
    }

    public void setCollectSet(Set<Collect> collectSet) {
        this.collectSet = collectSet;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getReport() {
        return Report;
    }

    public void setReport(Integer report) {
        Report = report;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getArticleId() {
        return ArticleId;
    }

    public void setArticleId(Integer id) {
        this.ArticleId = id;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public String getSelectRegion() {
        return selectRegion;
    }

    public void setSelectRegion(String selectRegion) {
        this.selectRegion = selectRegion;
    }

    public String getEnterAddressName() {
        return enterAddressName;
    }

    public void setEnterAddressName(String enterAddressName) {
        this.enterAddressName = enterAddressName;
    }

    public Double getEnterAddressLng() {
        return enterAddressLng;
    }

    public void setEnterAddressLng(Double enterAddressLng) {
        this.enterAddressLng = enterAddressLng;
    }

    public Double getEnterAddressLat() {
        return enterAddressLat;
    }

    public void setEnterAddressLat(Double enterAddressLat) {
        this.enterAddressLat = enterAddressLat;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getTextEditor() {
        return textEditor;
    }

    public void setTextEditor(String textEditor) {
        this.textEditor = textEditor;
    }

    public String getFreeTag() {
        return freeTag;
    }

    public void setFreeTag(String freeTag) {
        this.freeTag = freeTag;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + ArticleId +
                ", subjectCategory='" + subjectCategory + '\'' +
                ", selectRegion='" + selectRegion + '\'' +
                ", enterAddressName='" + enterAddressName + '\'' +
                ", enterAddressLng=" + enterAddressLng +
                ", enterAddressLat=" + enterAddressLat +
                ", articleTitle='" + articleTitle + '\'' +
                ", textEditor='" + textEditor + '\'' +
                ", freeTag='" + freeTag + '\'' +
                ", recommend=" + recommend +
                ", Report=" + Report +
                ", collect=" + collect +
                ", createDate=" + createDate +
                '}';
    }
}
