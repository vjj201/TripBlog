package com.java017.tripblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author YuCheng
 * @date 2021/11/12 - 下午 03:44
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/index")
    public String adminIndexPage() {
        return "/admin/admin_index";
    }

    @GetMapping("/productPage")
    public String showProductsManagePage() {return "/admin/admin_product"; }

    @GetMapping("/brandPage")
    public String brandPage() {
        return "/admin/admin_brand";
    }

    @GetMapping("/productSortPage")
    public String productSortPage() {
        return "/admin/admin_product_sort";
    }

    @GetMapping("/orderPage")
    public String orderPage() {
        return "/admin/admin_order";
    }

    @GetMapping("/userPage")
    public String memberPage() {
        return "/admin/admin_member";
    }

    @GetMapping("/messagePage")
    public String messagePage() {
        return "/admin/admin_message";
    }
    @GetMapping("/discountPage")
    public String discountPage() { return "/admin/admin_discount"; }

    @GetMapping("/reportPage")
    public String reportPage() {
        return "/admin/admin_report";
    }


}
