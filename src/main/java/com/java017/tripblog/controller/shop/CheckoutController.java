package com.java017.tripblog.controller.shop;

import com.java017.tripblog.service.CityService;
import com.java017.tripblog.vo.CheckoutSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 04:11
 */

@Controller
@SessionAttributes("checkout")
@RequestMapping("/shop")
public class CheckoutController {

    @Autowired
    private CityService cityService;

    //配送方式頁
    @GetMapping("/deliver")
    public String deliverPage(@SessionAttribute(name = "checkout", required = false) CheckoutSession checkoutSession) {

        return "/shop/shop_deliver";
    }

    //縣市顯示
    @GetMapping("/deliver/city")
    public String getCity(@RequestParam String location, Model model) {
        model.addAttribute("cityList", cityService.findAllCityByLocation(location));
        return "/shop/shop_deliver :: #city";
    }

    //區域顯示
    @GetMapping("/deliver/district")
    public String getDistrict(@RequestParam String cityName, Model model) {

        model.addAttribute("districtList", cityService.findAllDistrictByCityName(cityName));
        return "/shop/shop_deliver :: #district";
    }

    //收件提交
    @PostMapping("/deliver/done")
    @ResponseBody
    public void deliverDone(@RequestBody CheckoutSession checkoutSession, Model model) {
        model.addAttribute("checkout", checkoutSession);
    }

    //付款方式頁
    @GetMapping("/payment")
    public String paymentPage(@ModelAttribute("checkout") CheckoutSession checkoutSession, Model model) {
        System.out.println(checkoutSession);
        return "/shop/shop_payment";
    }

    //確認訂單頁
    @GetMapping("/confirm")
    public String confirmPage() {
        return "/shop/shop_confirm";
    }

    //訂單完成頁
    @GetMapping("/done")
    public String donePage(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "/shop/shop_done";
    }


}
