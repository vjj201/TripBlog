package com.java017.tripblog.entity;

import javax.persistence.*;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 09:30
 */

@Entity
@Table(name = "ShopCart")
public class ShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopCartId;

    private Long userId;

    @OneToOne
    @JoinColumn
    private ProductListInCart productListInCart;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(Long shopCartId) {
        this.shopCartId = shopCartId;
    }

    public ProductListInCart getProductListInCart() {
        return productListInCart;
    }

    public void setProductListInCart(ProductListInCart productListInCart) {
        this.productListInCart = productListInCart;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "shopCartId=" + shopCartId +
                ", userId=" + userId +
                ", productListInCart=" + productListInCart +
                '}';
    }
}
