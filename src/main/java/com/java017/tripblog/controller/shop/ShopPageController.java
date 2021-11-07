package com.java017.tripblog.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author YuCheng
 * @date 2021/10/31 - 下午 09:35
 */

@Controller
public class ShopPageController {

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

}
