package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.City;
import com.java017.tripblog.entity.District;
import com.java017.tripblog.repository.CityRepository;
import com.java017.tripblog.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 03:58
 */

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAllCity() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> findAllCityByLocation(String location) {
        return cityRepository.findAllByLocation(location);
    }

    @Override
    public List<District> findAllDistrictByCityName(String cityName) {
        City city = cityRepository.findByCityName(cityName);
        return city != null ? city.getDistrictList() : null;
    }
}
