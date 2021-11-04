package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.repository.BrandRepository;
import com.java017.tripblog.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/5 - 上午 12:22
 */

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand createOrUpdateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrandById(Long brandId) {
        brandRepository.deleteById(brandId);
    }

    @Override
    public Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId).orElse(null);
    }

    @Override
    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public Long countBrand() {
        return brandRepository.count();
    }
}
