package com.java017.tripblog.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:58
 */

@Entity
@Table(name = "ProductOrder")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOrderId;

    //訂單金額
    private Integer amounts;

    //訂單狀態(待出貨-1、運送中0、已收件1)
    private Integer orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;

    @ManyToOne
    @JoinColumn
    private User user;

    //訂單的購買量
    @OneToOne
    @JoinColumn
    private ProductListInOrder productListInOrder;

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = user;
    }

    public ProductListInOrder getProductListInOrder() {
        return productListInOrder;
    }

    public void setProductListInOrder(ProductListInOrder productListInOrder) {
        this.productListInOrder = productListInOrder;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "productOrderId=" + productOrderId +
                ", amounts=" + amounts +
                ", orderStatus=" + orderStatus +
                ", orderTime=" + orderTime +
                ", user=" + user +
                ", productListInOrder=" + productListInOrder +
                '}';
    }
}
