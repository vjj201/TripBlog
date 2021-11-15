package com.java017.tripblog.service;

import com.java017.tripblog.entity.Brand;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/5 - 上午 12:21
 */
public interface BrandService {

    //新增或修改
    Brand createOrUpdateBrand(Brand brand);

    //刪除
    void deleteBrandById(Long brandId);

    //查詢
    Brand findBrandById(Long brandId);

    //查詢全部
    List<Brand> findAllBrand();

    //數量
    Long countBrand();
}

