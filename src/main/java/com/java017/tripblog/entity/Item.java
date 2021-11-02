package com.java017.tripblog.entity;

import javax.persistence.*;

/**
 * @author YuCheng
 * @date 2021/11/2 - 下午 06:28
 */

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    private String title;

}
