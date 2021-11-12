package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Product;
import com.java017.tripblog.service.BrandService;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.service.ProductTagService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 04:11
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    //商品管理頁
    @GetMapping("/product/manage")
    public ResponseEntity<String> showProductsManage() {
        try {
            List<JSONObject> productsInfo = new ArrayList<>();
            for (Long i = 1L; i <= productService.countProduct(); i++) {
                Product product = productService.findProductById(i);
                System.out.println(product);

                JSONObject JSONproduct = new JSONObject();
                JSONproduct.put("productID", i);
                JSONproduct.put("productName", product.getProductName());
                JSONproduct.put("aboutProduct", product.getAboutProduct());
                JSONproduct.put("productDetail", product.getProductDetail());
                JSONproduct.put("price", product.getPrice());
                JSONproduct.put("inStock", product.getInStock());
                JSONproduct.put("alreadySold", product.getAlreadySold());
                JSONproduct.put("brand", product.getBrand().getBrandName());
                JSONproduct.put("productTag", product.getProductTag().getTagName());

                productsInfo.add(JSONproduct);
                System.out.println(productsInfo);
            }
            return new ResponseEntity<>(productsInfo.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
