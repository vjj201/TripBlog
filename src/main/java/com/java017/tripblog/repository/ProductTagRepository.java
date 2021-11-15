package com.java017.tripblog.repository;

import com.java017.tripblog.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/2 - 下午 11:17
 */

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

}
