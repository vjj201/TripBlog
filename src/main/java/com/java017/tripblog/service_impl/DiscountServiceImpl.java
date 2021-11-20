package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Discount;
import com.java017.tripblog.repository.DiscountRepository;
import com.java017.tripblog.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leepeishan
 * @date 2021/11/20 - 3:36 下午
 */

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount createOrUpdateDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public void deleteDiscountById(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public Discount findDiscountById(Long id) {
        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Discount> findAllDiscount() {
        return discountRepository.findAll();
    }
}
