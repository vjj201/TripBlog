package com.java017.tripblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:32
 */

@Entity
@Table(name = "Brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //品牌名稱
    private String brandName;

    //關於品牌
    private String aboutBrand;

    //店家位置
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    private List<Product> productList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getAboutBrand() {
        return aboutBrand;
    }

    public void setAboutBrand(String aboutBrand) {
        this.aboutBrand = aboutBrand;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", aboutBrand='" + aboutBrand + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
