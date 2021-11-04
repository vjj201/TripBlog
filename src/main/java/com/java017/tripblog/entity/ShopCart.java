package com.java017.tripblog.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author YuCheng
 * @date 2021/11/1 - 下午 09:30
 */

@Entity
@Table(name = "ShopCart")
public class ShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    //訂單細項
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopCartItems", referencedColumnName = "id")
    Set<Item> shopCartItems = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Item> getShopCartItems() {
        return shopCartItems;
    }

    public void setShopCartItems(Set<Item> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "id=" + id +
                ", user=" + user +
                ", shopCartItems=" + shopCartItems +
                '}';
    }
}
