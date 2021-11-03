package com.java017.tripblog.service;

import com.java017.tripblog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author YuCheng
 * @date 2021/11/2 - 下午 11:19
 */

@Service
public interface ProductService {

    //新增
    void createProduct(Product product);

    //id刪除
    void deleteProductById(Long ProductId);

    //id更新
    Product updateProductById(Product product);

    //id查詢
    Product findById(Long ProductId);

    //商品總數
    Long countProduct();

    //根據頁數查詢排序
    Page<Product> findPageOrderBy(int page, int size, Sort sort);

    //條件查詢
    
}
