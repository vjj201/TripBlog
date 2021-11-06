package com.java017.tripblog.service;

import com.java017.tripblog.entity.City;
import com.java017.tripblog.entity.District;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 03:51
 */
public interface CityService {

    List<City> findAllCity();

    List<City> findAllCityByLocation(String location);

    List<District> findAllDistrictById(Long id);

}
