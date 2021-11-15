package com.java017.tripblog.controller.shop;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductSort;
import com.java017.tripblog.service.BrandService;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.service.ProductSortService;
import com.java017.tripblog.vo.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * @author YuCheng
 * @date 2021/11/4 - 下午 03:42
 */

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ProductSortService productSortService;
    private final ProductService productService;
    private final BrandService brandService;

    @Autowired
    public ShopController(ProductSortService productSortService, ProductService productService, BrandService brandService) {
        this.productSortService = productSortService;
        this.productService = productService;
        this.brandService = brandService;
    }

    //商城首頁
    @GetMapping("")
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

    //購物車頁
    @GetMapping("/shopcart")
    public String shopCartPage() {
        return "/shop/shop_shopcart";
    }

    @PostMapping("/{page}")
    public String searchProduct(@PathVariable int page, @RequestBody ProductQuery productQuery, Model model) {
        Page<Product> productPage = productService.findProductPageByQuery(page, productQuery);

        int totalPages = productPage.getTotalPages();

        if(totalPages < 1) {
            return "/shop/notFound";
        }

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);

        return "/shop/shop_index :: #main";
    }

    //商品詳細頁
    @GetMapping("/product/{id}")
    public String productInfoPage(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        System.out.println("產品資訊 ：" + product);
        model.addAttribute("product", product);
        return "/shop/shop_product_info";
    }

    //顯示產品封面
    @RequestMapping(value = "/productPic/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getProductImage(@PathVariable Long id) throws IOException {
        String dir = "src/main/resources/static/images/shop/product/" + id + ".jpg";
        File file = new File(dir);
        return Files.readAllBytes(file.toPath());
    }

    //檢查商品庫存
    @ResponseBody
    @PostMapping("/productStock/{id}")
    public ResponseEntity<String> checkProductStock(@PathVariable Long id, @RequestParam Map<String, String> params, SessionStatus sessionStatus) {

        Product product = productService.findProductById(id);
        System.out.println("產品資訊 ：" + product);
        StringBuilder message = new StringBuilder();

        if(product.getInStock() < Integer.parseInt(params.get("count"))){
            message.append(product.getProductName()).append("庫存不足（庫存：")
                    .append(product.getInStock()).append("），請更改訂購數量");
        }

        if (message.length() != 0) {
            return new ResponseEntity<>(message.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            return new ResponseEntity<>(message.toString(), HttpStatus.ACCEPTED);
        }
    }
}
