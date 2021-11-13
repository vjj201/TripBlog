package com.java017.tripblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:52
 */

@Entity
@Table(name = "ProductTag")
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //分類名稱
    @Column(unique = true, nullable = false)
    private String tagName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productTag")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductTag)) {
            return false;
        }
        ProductTag otherTag = (ProductTag)obj;
        if(getId() == null || otherTag.getId() == null) {
            return otherTag.getTagName().equals(getTagName());
        }
        return otherTag.getId().equals(getId());
    }

    @Override
    public String toString() {
        return "ProductTag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
