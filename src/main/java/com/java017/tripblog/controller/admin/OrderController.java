package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.service.ProductOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/14 - 下午 08:04
 */

@Transactional
@RestController
@RequestMapping("/admin")
public class OrderController {

    private final ProductOrderService productOrderService;

    public OrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @GetMapping("/order")
    public ResponseEntity<List<ProductOrder>> findAllProductOrder() {
        List<ProductOrder> productOrderList = productOrderService.findAll();
        if (productOrderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productOrderList,HttpStatus.OK);
    }
}
