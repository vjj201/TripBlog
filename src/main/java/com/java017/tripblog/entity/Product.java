package com.java017.tripblog.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:20
 */

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    //產品名稱
    private String ProductName;

    //上市日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date launchedTime;

    //價格
    private Integer price;

    //庫存
    private Integer inStock;

    //已賣
    private Integer alreadySold;

    //商品簡介
    private String aboutProduct;

    @ManyToOne
    @JoinColumn
    private Brand brand;

    @ManyToOne
    @JoinColumn
    private ProductSort productSort;

    public ProductSort getProductSort() {
        return productSort;
    }

    public void setProductSort(ProductSort ProductSort) {
        this.productSort = ProductSort;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Date getLaunchedTime() {
        return launchedTime;
    }

    public void setLaunchedTime(Date launchedTime) {
        this.launchedTime = launchedTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getAlreadySold() {
        return alreadySold;
    }

    public void setAlreadySold(Integer alreadySold) {
        this.alreadySold = alreadySold;
    }

    public String getAboutProduct() {
        return aboutProduct;
    }

    public void setAboutProduct(String aboutProduct) {
        this.aboutProduct = aboutProduct;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", ProductName='" + ProductName + '\'' +
                ", launchedTime=" + launchedTime +
                ", price=" + price +
                ", inStock=" + inStock +
                ", alreadySold=" + alreadySold +
                ", aboutProduct='" + aboutProduct + '\'' +
                ", brand=" + brand +
                ", ProductSort=" + productSort +
                '}';
    }
}
