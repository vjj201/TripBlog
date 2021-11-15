package com.java017.tripblog.service;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/9 - 下午 10:31
 */
public interface ProductOrderService {
    ProductOrder createOrUpdate(ProductOrder productOrder);

    ProductOrder findById(Long id);

    ProductOrder findByUuid(String uuid);

    List<ProductOrder> findAll();

    List<ProductOrder> findAllByUser(User user);

    void deleteById(Long id);

    //條件查詢
    Page<ProductOrder> findProductOrderPageByQuery(int page, ProductOrder productOrder);
}
