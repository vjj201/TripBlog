package com.java017.tripblog.service;

import com.java017.tripblog.entity.Product;
import com.java017.tripblog.vo.ProductQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author YuCheng
 * @date 2021/11/2 - 下午 11:19
 */

public interface ProductService {

    //新增
    Product createOrUpdateProduct(Product product);

    //id刪除
    void deleteProductById(Long productId);

    //id查詢
    Product findProductById(Long productId);

    //商品總數
    Long countProduct();

    //根據頁數查詢排序
    Page<Product> findProductPageOrderBy(int page, int size, Sort sort);

    //條件查詢
    Page<Product> findProductPageByQuery(Pageable pageable, ProductQuery productQuery);
}
