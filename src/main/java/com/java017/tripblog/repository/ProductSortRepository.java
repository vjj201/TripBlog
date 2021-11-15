package com.java017.tripblog.repository;

import com.java017.tripblog.entity.ProductSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author YuCheng
 * @date 2021/11/2 - 下午 11:16
 */

@Repository
public interface ProductSortRepository extends JpaRepository<ProductSort, Long> {
}
