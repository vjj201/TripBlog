package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.Item;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.CityService;
import com.java017.tripblog.service.ProductOrderService;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.service.UserService;
import com.java017.tripblog.vo.CheckoutSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 04:11
 */

@Controller
@SessionAttributes("checkout")
@RequestMapping("/shop")
public class CheckoutController {

    private final CityService cityService;
    private final ProductService productService;
    private final ProductOrderService productOrderService;
    private final UserService userService;

    @Autowired
    public CheckoutController(CityService cityService, ProductService productService, ProductOrderService productOrderService, UserService userService) {
        this.cityService = cityService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.userService = userService;
    }

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

    //訂單完成
    @Transactional
    @ResponseBody
    @PostMapping("/done")
    public ResponseEntity<String> donePage(@SessionAttribute(name = "checkout", required = false) CheckoutSession checkout,
                                           @RequestBody List<Item> itemList,
                                           HttpSession session,
                                           SessionStatus sessionStatus) {
        System.out.println("donePage:" + checkout);
        String message = "";
        int amounts = 0;
        for (Item item : itemList) {
            Product product = productService.findProductById(item.getProductId());

            int orderQuantity = item.getQuantity();
            int inStock = product.getInStock();

            if (inStock >= orderQuantity) {
                product.setInStock(inStock - orderQuantity);
                product.setAlreadySold(product.getAlreadySold() + orderQuantity);
                amounts += product.getPrice() * orderQuantity;
            } else {
                message += item.getTitle() + "庫存少於" + orderQuantity + "\n";
            }
        }

        if (!"".equals(message)) {
            System.out.println("訂單有錯誤");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        amounts += checkout.getFreight();
        System.out.println("訂單總金額" + amounts);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setAmounts(amounts);
        productOrder.setReceiver(checkout.getReceiver());
        productOrder.setFreight(checkout.getFreight());
        productOrder.setAddress(checkout.getLocation() + checkout.getCity() + checkout.getDistrict() + checkout.getAddress());
        productOrder.setDeliver(checkout.getDeliver());
        productOrder.setPayment(checkout.getPayment());
        productOrder.setCardOwner(checkout.getCardOwner());
        productOrder.setCardNumber(checkout.getCardNumber());
        productOrder.setOrderItems(itemList);
        productOrder.setOrderStatus(-1);
        productOrder.setOrderTime(new Date());
        if (session.getAttribute("user") == null) {
            productOrderService.createOrUpdate(productOrder);
            message = "親愛的訪客，感謝您的購買，訂單編號:" +
                    productOrder.getId().toString() +
                    "，請妥善保存";
            sessionStatus.setComplete();
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        }

        Long userId = userService.getCurrentUser().getId();
        User user = userService.findUserById(userId);
        productOrder.setUser(user);
        productOrderService.createOrUpdate(productOrder);

        message = "Dear" + user.getNickname() + "，感謝您的購買，訂單編號:" +
                productOrder.getId().toString() +
                "，請妥善保存";

        sessionStatus.setComplete();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //已購買清單
    @GetMapping("/orderList")
    public String orderListPage(Model model,HttpSession session) {
        User user = (User)session.getAttribute("user");
        List<ProductOrder> productOrderList = productOrderService.findAllByUser(userService.findUserById(user.getId()));
        model.addAttribute("productOrderList", productOrderList);
        return "/shop/shop_order_list";
    }


}
