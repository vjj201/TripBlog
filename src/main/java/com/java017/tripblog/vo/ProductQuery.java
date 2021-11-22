package com.java017.tripblog.vo;

import org.springframework.data.domain.Sort;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 05:32
 */

public class ProductQuery {

    private String productName;
    private Integer brandId;
    private Integer tagId;
    private Integer sort;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getBrandId() {
        return brandId == null || brandId == 0 ? null : brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getTagId() {
        return tagId == null || tagId == 0 ? null : tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Sort getSort() {

        if(sort.equals(2)) {
            return Sort.by("launchedTime");
        }

        if(sort.equals(3)) {
            return Sort.by("price");
        }

        if(sort.equals(4)) {
            return Sort.by("price").descending();
        }

        if(sort.equals(5)) {
            return Sort.by("inStock");
        }

        if(sort.equals(6)) {
            return Sort.by("inStock").descending();
        }

        if(sort.equals(7)) {
            return Sort.by("alreadySold");
        }

        if(sort.equals(8)) {
            return Sort.by("alreadySold").descending();
        }

        return Sort.by("launchedTime").descending();
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ProductQuery{" +
                "productName='" + productName + '\'' +
                ", brandId=" + brandId +
                ", tagId=" + tagId +
                ", sort=" + sort +
                '}';
    }
}
