package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.Item;
import com.java017.tripblog.service.CityService;
import com.java017.tripblog.vo.CheckoutSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

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
    public String deliverPage() {
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

    //收件方式提交
    @PostMapping("/deliver/done")
    @ResponseBody
    public void deliverDone(@RequestBody CheckoutSession checkoutSession,
                            @SessionAttribute(name = "checkout", required = false) CheckoutSession checkout,
                            Model model) {
        if (!ObjectUtils.isEmpty(checkout)) {
            checkout.setReceiver(checkoutSession.getReceiver());
            checkout.setLocation(checkoutSession.getLocation());
            checkout.setCity(checkoutSession.getCity());
            checkout.setDistrict(checkoutSession.getDistrict());
            checkout.setAddress(checkoutSession.getAddress());
            checkout.setDeliver(checkoutSession.getDeliver());
            checkout.setFreight(checkoutSession.getFreight());
            model.addAttribute("checkout", checkout);

            System.out.println(checkout);
            return;
        }
        System.out.println(checkoutSession);
        model.addAttribute("checkout", checkoutSession);
    }

    //付款方式頁
    @GetMapping("/payment")
    public String paymentPage() {
        return "/shop/shop_payment";
    }

    //付款方式提交
    @PostMapping("/payment/done")
    @ResponseBody
    public void paymentDone(@RequestBody CheckoutSession checkoutSession,
                            @SessionAttribute(name = "checkout", required = false) CheckoutSession checkout,
                            Model model) {
        checkout.setPayment(checkoutSession.getPayment());
        checkout.setCardOwner(checkoutSession.getCardOwner());
        checkout.setCardNumber(checkoutSession.getCardNumber());
        model.addAttribute("checkout", checkout);
    }


    //確認訂單頁
    @GetMapping("/confirm")
    public String confirmPage() {
        return "/shop/shop_confirm";
    }

    //訂單完成頁
    @PostMapping("/done")
    public String donePage(@SessionAttribute(name = "checkout", required = false) CheckoutSession checkout,
                           @RequestBody List<Item> itemList,
                           SessionStatus sessionStatus) {
        System.out.println("donePage");

        for(Item item : itemList) {
            System.out.println(item);
        }
        sessionStatus.setComplete();
        return "/shop/shop_done";
    }


}
