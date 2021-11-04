package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductSort;
import com.java017.tripblog.service.BrandService;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.service.ProductSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/10/31 - 下午 09:35
 */

@Controller
public class ShopPageController {

    private final ProductSortService productSortService;
    private final ProductService productService;
    private final BrandService brandService;

    @Autowired
    public ShopPageController(ProductSortService productSortService, ProductService productService, BrandService brandService) {
        this.productSortService = productSortService;
        this.productService = productService;
        this.brandService = brandService;
    }

    //商城首頁
    @GetMapping("/shop")
    public String shopPage(Model model) {
        Page<Product> productPage = productService.findProductPageOrderBy(0, 9, Sort.by("launchedTime").descending());
        List<ProductSort> productSortList = productSortService.findAllProductSort();
        List<Brand> brandList = brandService.findAllBrand();

        model.addAttribute("brandList", brandList);
        model.addAttribute("sortList", productSortList);
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", 1);

        return "/shop/shop_index";
    }

    //商品詳細頁
    @GetMapping("/shop/product/{id}")
    public String productInfoPage(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        System.out.println("產品資訊 ：" + product);
        model.addAttribute("product", product);
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

    //顯示產品封面
    @RequestMapping(value = "/productPic/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getProductImage(@PathVariable Long id) throws IOException {
        String dir = "src/main/resources/static/images/shop/product/" + id + ".jpg";
        File file = new File(dir);
        return Files.readAllBytes(file.toPath());
    }
}
