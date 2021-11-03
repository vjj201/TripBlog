package com.java017.tripblog.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 08:58
 */

@Entity
@Table(name = "ProductOrder")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //訂單金額
    private Integer amounts;

    //訂單狀態(待出貨-1、運送中0、已收件1)
    private Integer orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    //訂單細項
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "orderItems", referencedColumnName = "id")
    Set<Item> orderItems = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<Item> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", amounts=" + amounts +
                ", orderStatus=" + orderStatus +
                ", orderTime=" + orderTime +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }
}
