package com.java017.tripblog.entity;

import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "subjectcategory")
    String subjectcategory;

    @Column(name = "selectregion")
    String selectregion;

    @Column(name = "enteraddress")
    String enteraddress;

    @Column(name = "articletitle")
    String articletitle;

    @Column(name = "texteditor")
    String texteditor;

    @Column(name = "free_tag")
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

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }

    public String getSubjectcategory() {
        return subjectcategory;
    }

    public void setSubjectcategory(String subjectcategory) {
        this.subjectcategory = subjectcategory;
    }

    public String getSelectregion() {
        return selectregion;
    }

    public void setSelectregion(String selectregion) {
        this.selectregion = selectregion;
    }

    public String getEnteraddress() {
        return enteraddress;
    }

    public void setEnteraddress(String enteraddress) {
        this.enteraddress = enteraddress;
    }

    public String getTexteditor() {
        return texteditor;
    }

    public void setTexteditor(String texteditor) {
        this.texteditor = texteditor;
    }
}
