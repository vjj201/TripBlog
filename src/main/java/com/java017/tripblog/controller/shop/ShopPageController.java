package com.java017.tripblog.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author YuCheng
 * @date 2021/10/31 - 下午 09:35
 */

@Controller
public class ShopPageController {

    //商城首頁
    @GetMapping("/shop")
    public String shopPage() {
        return "/shop/shop_index";
    }

    //商品詳細頁
    @GetMapping("/shop/productInfo")
    public String productInfoPage() {
        return "/shop/shop_product_info";
    }

    //購物車頁
    @GetMapping("/shop/shopcart")
    public String shopCartPage() {
        return "/shop/shop_shopcart";
    }

    //配送方式頁
    @GetMapping("/shop/deliver")
    public String deliverPage() {
        return "/shop/shop_deliver";
    }

    //付款方式頁
    @GetMapping("/shop/payment")
    public String paymentPage() {
        return "/shop/shop_payment";
    }

    //確認訂單頁
    @GetMapping("/shop/confirm")
    public String confirmPage() {
        return "/shop/shop_confirm";
    }

    //訂單完成頁
    @GetMapping("/shop/done")
    public String donePage() {
        return "/shop/shop_done";
    }

    //已購買清單
    @GetMapping("/shop/orderList")
    public String orderListPage() {
        return "/shop/shop_order_list";
    }


}
