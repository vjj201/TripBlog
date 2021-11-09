package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.Intro;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author YuCheng
 * @date 2021/10/31 - 下午 09:35
 */

@Controller
public class ShopPageController {

    private final ProductService productService;

    @Autowired
    public ShopPageController(ProductService productService) {
        this.productService = productService;
    }

    //購物車頁
    @GetMapping("/shop/shopcart")
    public String shopCartPage() {
        return "/shop/shop_shopcart";
    }


    //已購買清單
    @GetMapping("/shop/orderList")
    public String orderListPage() {
        return "/shop/shop_order_list";
    }

    //檢查商品庫存
    @ResponseBody
    @PostMapping("/shop/productStock/{id}")
    public boolean updateIntro(@PathVariable Long id, @RequestParam Map<String, String> params) {

        Product product = productService.findProductById(id);
        System.out.println("產品資訊 ：" + product);
        return product.getInStock() >= Integer.parseInt(params.get("count"));
    }
}