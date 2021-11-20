package com.java017.tripblog.service;

import com.java017.tripblog.entity.Discount;
import com.java017.tripblog.entity.Product;

import java.util.List;

/**
 * @author leepeishan
 * @date 2021/11/20 - 3:36 下午
 */

public interface DiscountService {

    //新增
    Discount createOrUpdateDiscount(Discount discount);

    //id刪除
    void deleteDiscountById(Long id);

    //id查詢
    Discount findDiscountById(Long id);

    Discount findDiscountByTitle(String title);

    //查詢全部
    List<Discount> findAllDiscount();

}
