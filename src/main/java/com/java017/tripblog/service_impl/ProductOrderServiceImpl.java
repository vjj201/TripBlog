package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ProductOrderRepository;
import com.java017.tripblog.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ProductOrder findByUser(User user) {
        return productOrderRepository.findByUser(user);
    }

    @Override
    public List<ProductOrder> findAllByUser(User user) {
        return productOrderRepository.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        productOrderRepository.deleteById(id);
    }
}
