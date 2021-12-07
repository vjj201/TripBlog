package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductTag;
import com.java017.tripblog.repository.ProductRepository;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.vo.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/3 - 下午 06:52
 */

@Service
@Transactional(rollbackForClassName = "RuntimeException")
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
    public List<Product> findAllProduct() { return productRepository.findAll();}

    @Override
    public Page<Product> findProductPageOrderBy(int page, int size, Sort sort) {
        return productRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Product> findProductPageByQuery(int page, ProductQuery productQuery) {

        PageRequest pageRequest = PageRequest.of(page-1, 9, productQuery.getSort());

        return productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //條件表單
                List<Predicate> predicateList = new ArrayList<>();

                if(!ObjectUtils.isEmpty(productQuery)) {
                    //判斷第一個條件輸入的標題不為空
                    if (!ObjectUtils.isEmpty(productQuery.getProductName())) {
                        //添加語句語法和欄位名稱，第二個參數為屬性值，like前後需加百分號
                        predicateList.add(criteriaBuilder.like(root.<String>get("productName"), "%" + productQuery.getProductName() + "%"));
                    }

                    //判斷第二個條件分類選單，內容不為空
                    if (!ObjectUtils.isEmpty(productQuery.getBrandId())) {
                        predicateList.add(criteriaBuilder.equal(root.<Brand>get("brand").get("id"), productQuery.getBrandId()));
                    }

                    //判斷第三個條件分類選單，內容不為空
                    if (!ObjectUtils.isEmpty(productQuery.getTagId())) {
                        predicateList.add(criteriaBuilder.equal(root.<ProductTag>get("productTag"), productQuery.getTagId()));
                    }
                }

                //動態條件傳入語句，清單需轉為陣列
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageRequest);

    }
}
