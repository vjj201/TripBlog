package com.java017.tripblog.repository;

import com.java017.tripblog.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 03:48
 */

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}
