package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ProductOrderRepository;
import com.java017.tripblog.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/9 - 下午 10:33
 */

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public ProductOrder createOrUpdate(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public ProductOrder findById(Long id) {
        return productOrderRepository.findById(id).orElse(null);
    }

    @Override
    public ProductOrder findByUuid(String uuid) {
        return productOrderRepository.findByUuid(uuid);
    }

    @Override
    public List<ProductOrder> findAll() {
        return productOrderRepository.findAll();
    }

    @Override
    public List<ProductOrder> findAllByUser(User user) {
        return productOrderRepository.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        productOrderRepository.deleteById(id);
    }

    @Override
    public Page<ProductOrder> findProductOrderPageByQuery(int page, ProductOrder productOrder) {
        PageRequest pageRequest = PageRequest.of(page - 1, 8, Sort.by("orderTime").descending());
        return productOrderRepository.findAll(new Specification<ProductOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductOrder> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                //條件表單
                List<Predicate> predicateList = new ArrayList<>();

                if (!ObjectUtils.isEmpty(productOrder)) {
                    //訂單狀態
                    if (!ObjectUtils.isEmpty(productOrder.getOrderStatus())) {
                        predicateList.add(criteriaBuilder.equal(root.get("orderStatus"), productOrder.getOrderStatus()));
                    }
                    //會員帳號
                    if (!ObjectUtils.isEmpty(productOrder.getUsername())) {
                        predicateList.add(criteriaBuilder.like(root.get("username"), "%" + productOrder.getUsername() + "%"));
                    }
                    //訂單編號
                    if (!ObjectUtils.isEmpty(productOrder.getUuid())) {
                        predicateList.add(criteriaBuilder.equal(root.get("uuid"), productOrder.getUuid()));
                    }
                }

                //動態條件傳入語句，清單需轉為陣列
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageRequest);
    }
}
