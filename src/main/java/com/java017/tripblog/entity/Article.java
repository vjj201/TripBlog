package com.java017.tripblog.entity;

import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String subjectCategory;

    String selectRegion;

    String enterAddress;

    String articleTitle;

    String textEditor;

    String free_tag;

    public String getFree_tag() {
        return free_tag;
    }

    public void setFree_tag(String free_tag) {
        this.free_tag = free_tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
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

    public String getEnterAddress() {
        return enterAddress;
    }

    public void setEnterAddress(String enterAddress) {
        this.enterAddress = enterAddress;
    }

    public String getTextEditor() {
        return textEditor;
    }

    public void setTextEditor(String textEditor) {
        this.textEditor = textEditor;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", subjectCategory='" + subjectCategory + '\'' +
                ", selectRegion='" + selectRegion + '\'' +
                ", enterAddress='" + enterAddress + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", textEditor='" + textEditor + '\'' +
                ", free_tag='" + free_tag + '\'' +
                '}';
    }
}
