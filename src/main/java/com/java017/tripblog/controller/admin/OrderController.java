package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.service.ProductOrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ProductOrder>> findAllOrder() {
        List<ProductOrder> productOrderList = productOrderService.findAll();
        if (productOrderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productOrderList, HttpStatus.OK);
    }

    @PostMapping("/order/page/{page}")
    public ResponseEntity<Page<ProductOrder>> findAllOrderByQuery(@PathVariable int page,
                                                                  @RequestBody(required = false) ProductOrder productOrder) {
        Page<ProductOrder> orderPage = productOrderService.findProductOrderPageByQuery(page, productOrder);
        if (orderPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderPage, HttpStatus.OK);
    }

    @GetMapping("/order/{uuid}")
    public ResponseEntity<ProductOrder> findOrderbyUUID(@PathVariable String uuid) {
        ProductOrder productOrder = productOrderService.findByUuid(uuid);
        System.out.println(productOrder);
        if (productOrder.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productOrder, HttpStatus.OK);
    }
}
