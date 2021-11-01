package com.java017.tripblog.entity;

import javax.persistence.*;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:52
 */

@Entity
@Table(name = "ProductTag")
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productTagId;

    //分類名稱
    private String tagName;


    public Long getProductTagId() {
        return productTagId;
    }

    public void setProductTagId(Long productTagId) {
        this.productTagId = productTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "ProductTag{" +
                "productTagId=" + productTagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
