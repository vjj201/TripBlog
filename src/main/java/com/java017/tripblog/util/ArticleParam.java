package com.java017.tripblog.util;



public class ArticleParam  {


    Integer id;
    String subjectCategory;
    String selectRegion;
    String enterAddress;
    String articleTitle;
    String textEditor;
    String[] free_Tags;

    public String[] getFree_Tags() {
        return free_Tags;
    }

    public void setFree_Tags(String[] freeTag) {
        this.free_Tags = freeTag;
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


}