package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.*;
import com.java017.tripblog.service.*;
import com.java017.tripblog.socket.AdminSocket;
import com.java017.tripblog.util.OrderIdCreator;
import com.java017.tripblog.vo.CheckoutSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
    private final DiscountService discountService;

    @Autowired
    public CheckoutController(CityService cityService, ProductService productService, ProductOrderService productOrderService, UserService userService, DiscountService discountService) {
        this.cityService = cityService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.userService = userService;
        this.discountService = discountService;
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

    //折扣輸入
    @Transactional
    @PostMapping("/discount")
    @ResponseBody
    public ResponseEntity<Integer> getDiscount(@RequestBody Discount discount,
                                               @SessionAttribute(name = "checkout", required = false) CheckoutSession checkout,
                                               Model model) {
        Discount discountByTitle = discountService.findDiscountByTitle(discount.getTitle());
        if(!ObjectUtils.isEmpty(checkout.getDiscountTitle())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(ObjectUtils.isEmpty(discountByTitle)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Date expiredTime = discountByTitle.getExpiredTime();
        Date today = new Date();

        if (expiredTime.before(today)) {
            System.out.println("優惠過期");
            discountService.deleteDiscountById(discountByTitle.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        checkout.setDiscountTitle(discountByTitle.getTitle());
        model.addAttribute("checkout", checkout);
        return new ResponseEntity<>(discountByTitle.getDiscountNumber(),HttpStatus.OK);
    }

    //訂單完成
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @ResponseBody
    @PostMapping("/done")
    public ResponseEntity<String> donePage(@SessionAttribute(name = "checkout", required = false) CheckoutSession checkout,
                                           @RequestBody List<Item> itemList,
                                           HttpSession session,
                                           SessionStatus sessionStatus) {


        StringBuilder message = new StringBuilder();
        int amounts = 0;
        for (Item item : itemList) {
            Product product = productService.findProductById(item.getProductId());

            int orderQuantity = item.getQuantity();
            int inStock = product.getInStock();

            if (inStock >= orderQuantity) {
                product.setInStock(inStock - orderQuantity);
                product.setAlreadySold(product.getAlreadySold() + orderQuantity);
                Integer price = product.getPrice();

                if (price.equals(item.getPrice())) {
                    amounts += price * orderQuantity;
                } else {
                    message.append(item.getTitle()).append("金額不符.");
                }


            } else {
                message.append(item.getTitle()).append("庫存少於").append(orderQuantity).append(".");
            }
        }

        if (message.length() != 0) {
            try {
                throw new RuntimeException("訂單有錯誤:" + message);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return new ResponseEntity<>(message.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        amounts += checkout.getFreight();
        if(!ObjectUtils.isEmpty(checkout.getDiscountTitle())) {
            int discountNumber = discountService.findDiscountByTitle(checkout.getDiscountTitle()).getDiscountNumber();
            amounts =  (int)Math.ceil(amounts * discountNumber / 10);
        }
        System.out.println("訂單總金額" + amounts);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUuid(OrderIdCreator.createOrderNumber());
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
            message.append("親愛的訪客，感謝您的購買，訂單編號:").
                    append(productOrder.getUuid()).
                    append("，請妥善保存訂單，方便後續追蹤，完整功能也請註冊本站會員");
            sessionStatus.setComplete();
            return new ResponseEntity<>(message.toString(), HttpStatus.ACCEPTED);
        }

        Long userId = userService.getCurrentUser().getId();
        User user = userService.findUserById(userId);
        productOrder.setUser(user);
        productOrder.setUsername(user.getUsername());
        productOrderService.createOrUpdate(productOrder);
        message.append("Dear").append(user.getNickname()).
                append("，感謝您的購買，訂單編號:").
                append(productOrder.getUuid()).
                append("，請妥善保存");

        sessionStatus.setComplete();
        AdminSocket.sendInfo("有新的訂單");
        return new ResponseEntity<>(message.toString(), HttpStatus.OK);
    }

    //已購買清單
    @GetMapping("/orderList")
    public String orderListPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/user/loginPage";
        }
        List<ProductOrder> productOrderList = productOrderService.findAllByUser(userService.findUserById(user.getId()));

        model.addAttribute("productOrderList", productOrderList);
        return "/shop/shop_order_list";
    }

    //訂單詳細頁
    @GetMapping("/productOrder/{id}")
    public String productOrderPage(@PathVariable Long id, Model model) {
        ProductOrder productOrder = productOrderService.findById(id);
        model.addAttribute("productOrder", productOrder);
        model.addAttribute("itemList", productOrder.getOrderItems());
        return "/shop/shop_order_info";
    }


}
