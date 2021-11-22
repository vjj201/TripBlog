package com.java017.tripblog.controller;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author leepeishan
 * @date 2021/11/20 - 12:20 上午
 */
@Transactional
@Controller
public class IndexController {

    private  final ProductService productService;

    @Autowired
    public IndexController(ProductService productService) {
        this.productService = productService;
    }

    //獲取最新四筆商品資訊
    @GetMapping("/newestProduct")
    public ResponseEntity<Page<Product>> showNewestProduct() {
        try {
            Page<Product> products = productService.findProductPageOrderBy(0, 4, Sort.by(Sort.Order.desc("id")));
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
