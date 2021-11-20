package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author leepeishan
 * @date 2021/11/20 - 3:33 下午
 */

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
