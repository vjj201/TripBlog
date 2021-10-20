package com.java017.tripblog.controller.parameter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ArticleParam  {


    Integer id;
    String subjectcategory;
    String selectregion;
    String enteraddress;
    String articletitle;
    String texteditor;
    String[] free_Tags;

    public String[] getfree_Tags() {
        return free_Tags;
    }

    public void setfree_Tags(String[] freetag) {
        this.free_Tags = freetag;
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
