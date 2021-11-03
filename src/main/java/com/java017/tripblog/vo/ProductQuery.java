package com.java017.tripblog.vo;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 05:32
 */

public class ProductQuery {

    private String searchInput;
    private Integer productTagId;
    private Integer brandId;

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public Integer getProductTagId() {
        return productTagId;
    }

    public void setProductTagId(Integer productTagId) {
        this.productTagId = productTagId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "ProductQuery{" +
                "searchInput='" + searchInput + '\'' +
                ", productTagId=" + productTagId +
                ", brandId=" + brandId +
                '}';
    }
}
