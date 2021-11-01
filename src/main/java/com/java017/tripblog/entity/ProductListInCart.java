package com.java017.tripblog.entity;

import javax.persistence.*;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 10:39
 */

@Entity
@Table(name = "ProductListInCart")
public class ProductListInCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productListInCartId;

    private Long productId;

    private Integer quantity;

    public Long getProductListInCartId() {
        return productListInCartId;
    }

    public void setProductListInCartId(Long productListInCartId) {
        this.productListInCartId = productListInCartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductListInCart{" +
                "productListInCartId=" + productListInCartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
