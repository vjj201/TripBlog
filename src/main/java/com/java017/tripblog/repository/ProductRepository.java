package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/2 - 下午 10:42
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {

    Product findByProductName(String productName);

    List<Product> findAllByBrand(Brand brand);

    List<Product> findAllByProductTag(ProductTag productTag);

    List<Product> findByPriceGreaterThanEqual(Integer price);

}
