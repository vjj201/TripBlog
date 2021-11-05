package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.Product;
import com.java017.tripblog.service.BrandService;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.service.ProductSortService;
import com.java017.tripblog.vo.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author YuCheng
 * @date 2021/11/4 - 下午 03:42
 */

@Controller
public class ProductController {

    private final ProductSortService productSortService;
    private final ProductService productService;
    private final BrandService brandService;

    @Autowired
    public ProductController(ProductSortService productSortService, ProductService productService, BrandService brandService) {
        this.productSortService = productSortService;
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping("/shop/{page}")
    public String changeProductPage(@PathVariable int page, Model model) {
        Page<Product> productPage = productService.findProductPageOrderBy((page - 1), 9, Sort.by("launchedTime").descending());

        System.out.println("第一頁" +  productPage.isFirst());
        System.out.println("最後一頁" +  productPage.isLast());

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);

        return "/shop/shop_index :: #main";
    }

    @PostMapping("/shop/search")
    public String searchProduct(@RequestBody ProductQuery productQuery, Model model) {
        System.out.println(productQuery);
        Page<Product> productPage = productService.findProductPageByQuery(productQuery);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", 1);

        return "/shop/shop_index :: #main";
    }
}
