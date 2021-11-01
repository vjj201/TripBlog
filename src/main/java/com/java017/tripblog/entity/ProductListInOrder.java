package com.java017.tripblog.entity;

import javax.persistence.*;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 10:30
 */

@Entity
@Table(name = "ProductListInOrder")
public class ProductListInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productListInOrderId;

    private Long productId;

    private Integer quantity;

    public Long getProductListInOrderId() {
        return productListInOrderId;
    }

    public void setProductListInOrderId(Long productListInOrderId) {
        this.productListInOrderId = productListInOrderId;
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
        return "ProductListInOrder{" +
                "productListInOrderId=" + productListInOrderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
