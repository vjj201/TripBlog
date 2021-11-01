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
    private Long productSortId;

    //分類名稱
    private String sortName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<ProductTag> tagList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSort")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Long getProductSortId() {
        return productSortId;
    }

    public void setProductSortId(Long productSortId) {
        this.productSortId = productSortId;
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
                "productSortId=" + productSortId +
                ", sortName='" + sortName + '\'' +
                ", tagList=" + tagList +
                ", productList=" + productList +
                '}';
    }
}
