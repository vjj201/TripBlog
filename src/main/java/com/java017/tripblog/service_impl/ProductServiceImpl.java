package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Product;
import com.java017.tripblog.repository.ProductRepository;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.vo.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 06:52
 */

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }


    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Long countProduct() {
        return productRepository.count();
    }

    @Override
    public Page<Product> findProductPageOrderBy(int page, int size, Sort sort) {
        Page<Product> pageResult = productRepository.findAll(PageRequest.of(page, size, sort));
        return pageResult;
    }

    @Override
    public Page<Product> findProductPageByQuery(Pageable pageable, ProductQuery ProductQuery) {
        return null;
    }
}
