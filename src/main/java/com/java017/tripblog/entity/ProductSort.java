package com.java017.tripblog.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:50
 */

@Entity
@Table(name = "ProductSort")
public class ProductSort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //分類名稱
    @Column(unique = true, nullable = false)
    private String sortName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<ProductTag> tagList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<ProductTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<ProductTag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "ProductSort{" +
                "id=" + id +
                ", sortName='" + sortName + '\'' +
                ", tagList=" + tagList +
                '}';
    }
}
