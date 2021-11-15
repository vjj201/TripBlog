package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author YuCheng
 * @date 2021/11/5 - 上午 12:17
 */

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
}
