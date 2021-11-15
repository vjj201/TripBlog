package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.ProductSort;
import com.java017.tripblog.repository.ProductSortRepository;
import com.java017.tripblog.service.ProductSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 06:02
 */

@Service
public class ProductSortServiceImpl implements ProductSortService {

    private final ProductSortRepository productSortRepository;

    @Autowired
    public ProductSortServiceImpl(ProductSortRepository productSortRepository) {
        this.productSortRepository = productSortRepository;
    }

    @Override
    public ProductSort createOrUpdateProductSort(ProductSort productSort) {
        return productSortRepository.save(productSort);
    }

    @Override
    public void deleteProductSortById(Long productSortId) {
        productSortRepository.deleteById(productSortId);
    }

    @Override
    public ProductSort findProductSortById(Long productSortId) {
        return productSortRepository.findById(productSortId).orElse(null);
    }

    @Override
    public List<ProductSort> findAllProductSort() {
        return productSortRepository.findAll();
    }

    @Override
    public Long countProductSort() {
        return productSortRepository.count();
    }
}
