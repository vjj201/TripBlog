package com.java017.tripblog.service;

import com.java017.tripblog.entity.ProductTag;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 05:44
 */

public interface ProductTagService {

    //新增或更新
    ProductTag createOrUpdateProductTag(ProductTag productTag);

    //id刪除
    void deleteProductTagById(Long productTagId);

    //批量刪除
    void deleteByList(List<ProductTag> productTagList);

    //id查詢
    ProductTag findProductTagById(Long productTagId);

    //查詢全部
    List<ProductTag> findAllProductTag();

    //商品總數
    Long countProductTag();
}
