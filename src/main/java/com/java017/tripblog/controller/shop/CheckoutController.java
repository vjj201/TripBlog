package com.java017.tripblog.controller.shop;

import com.java017.tripblog.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 04:11
 */

@Controller
@RequestMapping("/shop")
public class CheckoutController {

    @Autowired
    private CityService cityService;

    @GetMapping("/deliver/city")
    public String getCity(@RequestParam String location, Model model) {
        model.addAttribute("cityList", cityService.findAllCityByLocation(location));
        return "/shop/shop_deliver :: #city";
    }


    @GetMapping("/deliver/district")
    public String getDistrict(@RequestParam Long id, Model model) {
        model.addAttribute("districtList", cityService.findAllDistrictById(id));
        return "/shop/shop_deliver :: #district";
    }
}
