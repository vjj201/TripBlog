package com.java017.tripblog.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "article")
public class Article {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String subjectCategory;

    String selectRegion;

    String enterAddressName;

    Double enterAddressLng;

    Double enterAddressLat;

    String articleTitle;

    String textEditor;

    String free_tag;

    Integer recommend;

    Integer Report;

    Integer collect;


    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createDate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFree_tag() {
        return free_tag;
    }

    public void setFree_tag(String free_tag) {
        this.free_tag = free_tag;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", subjectCategory='" + subjectCategory + '\'' +
                ", selectRegion='" + selectRegion + '\'' +
                ", enterAddressName='" + enterAddressName + '\'' +
                ", enterAddressLng=" + enterAddressLng +
                ", enterAddressLat=" + enterAddressLat +
                ", articleTitle='" + articleTitle + '\'' +
                ", textEditor='" + textEditor + '\'' +
                ", free_tag='" + free_tag + '\'' +
                ", recommend=" + recommend +
                ", Report=" + Report +
                ", collect=" + collect +
                ", createDate=" + createDate +
                '}';
    }
}
