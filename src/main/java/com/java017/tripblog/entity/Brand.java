package com.java017.tripblog.entity;

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
    private Long brandId;

    //品牌名稱
    private String brandName;

    //關於品牌
    private String aboutBrand;

    //商品種類數
    private Integer kindOfProduct;

    //店家位置
    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<Product> productList;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public Integer getKindOfProduct() {
        return kindOfProduct;
    }

    public void setKindOfProduct(Integer kindOfProduct) {
        this.kindOfProduct = kindOfProduct;
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
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", aboutBrand='" + aboutBrand + '\'' +
                ", kindOfProduct=" + kindOfProduct +
                ", location='" + location + '\'' +
                ", productList=" + productList +
                '}';
    }
}
