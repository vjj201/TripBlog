package com.java017.tripblog.util;



public class ArticleParam  {


    Integer id;
    String subjectCategory;
    String selectRegion;
    String enterAddressName;
    Double enterAddressLng;
    Double enterAddressLat;
    String articleTitle;
    String textEditor;
    String[] free_Tags;

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

    public String[] getFree_Tags() {
        return free_Tags;
    }

    public void setFree_Tags(String[] free_Tags) {
        this.free_Tags = free_Tags;
    }
}
