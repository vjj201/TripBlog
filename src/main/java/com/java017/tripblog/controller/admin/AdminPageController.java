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

    @GetMapping("")
    public String adminIndexPage() {
        return "/admin/admin_index";
    }

    @GetMapping("/productSortPage")
    public String productSortPage() {
        return "/admin/admin_product_sort";
    }
}
