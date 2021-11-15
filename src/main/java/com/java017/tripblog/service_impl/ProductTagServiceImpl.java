package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.ProductTag;
import com.java017.tripblog.repository.ProductTagRepository;
import com.java017.tripblog.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 06:19
 */

@Transactional
@Service
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;

    @Autowired
    public ProductTagServiceImpl(ProductTagRepository productTagRepository) {
        this.productTagRepository = productTagRepository;
    }

    @Override
    public ProductTag createOrUpdateProductTag(ProductTag productTag) {
        return productTagRepository.save(productTag);
    }

    @Override
    public void deleteProductTagById(Long productTagId) {
        productTagRepository.deleteById(productTagId);
    }

    @Override
    public void deleteByList(List<ProductTag> productTagList) {
        productTagRepository.deleteAllInBatch(productTagList);
    }

    @Override
    public ProductTag findProductTagById(Long productTagId) {
        return productTagRepository.findById(productTagId).orElse(null);
    }

    @Override
    public List<ProductTag> findAllProductTag() {
        return productTagRepository.findAll();
    }

    @Override
    public Long countProductTag() {
        return productTagRepository.count();
    }
}
