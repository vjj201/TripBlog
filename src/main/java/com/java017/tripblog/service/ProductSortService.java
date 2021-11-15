package com.java017.tripblog.service;

import com.java017.tripblog.entity.ProductSort;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 05:43
 */
public interface ProductSortService {

    //新增或更新
    ProductSort createOrUpdateProductSort(ProductSort productSort);

    //id刪除
    void deleteProductSortById(Long productSortId);


    //id查詢
    ProductSort findProductSortById(Long productSortId);

    //全部查詢
    List<ProductSort> findAllProductSort();

    //商品總數
    Long countProductSort();
}
