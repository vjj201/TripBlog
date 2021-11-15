package com.java017.tripblog.repository;

import com.java017.tripblog.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 03:47
 */

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByLocation(String location);
    City findByCityName(String cityName);
}
