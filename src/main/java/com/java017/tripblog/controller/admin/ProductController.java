package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductTag;
import com.java017.tripblog.service.BrandService;
import com.java017.tripblog.service.ProductService;
import com.java017.tripblog.service.ProductTagService;
import com.java017.tripblog.util.FileUploadUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sandy
 * @date 2021/11/6 - 下午 04:11
 */

@RestController
@RequestMapping("/admin")
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;
    private final ProductTagService productTagService;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService, ProductTagService productTagService) {
        this.productService = productService;
        this.brandService = brandService;
        this.productTagService = productTagService;
    }

    //查詢
    //商品管理頁
    @GetMapping("/product/manage")
    public ResponseEntity<String> showProductsManage() {
        try {
            List<JSONObject> productsInfo = new ArrayList<>();
            Long id = 1L;
            for (int i = 1; i <= productService.countProduct(); i++) {
                if (productService.findProductById(id) != null){
                    Product product = productService.findProductById(id);
                    System.out.println(product);
                    JSONObject JSONproduct = new JSONObject();
                    JSONproduct.put("productID", id);
                    JSONproduct.put("productName", product.getProductName());
                    JSONproduct.put("aboutProduct", product.getAboutProduct());
                    JSONproduct.put("productDetail", product.getProductDetail());
                    JSONproduct.put("price", product.getPrice());
                    JSONproduct.put("inStock", product.getInStock());
                    JSONproduct.put("alreadySold", product.getAlreadySold());
                    if (product.getBrand() != null) {
                        JSONproduct.put("brand", product.getBrand().getBrandName());
                    } else {
                        JSONproduct.put("brand", "未設定");
                    }
                    if (product.getProductTag() != null) {
                        JSONproduct.put("productTag", product.getProductTag().getTagName());
                    } else {
                        JSONproduct.put("productTag", "未設定");
                    }
                    productsInfo.add(JSONproduct);
                    System.out.println(productsInfo);
                    id++;
                } else {
                    id++;
                    i--;
                }
            }
            return new ResponseEntity<>(productsInfo.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //獲取商品資訊
    @GetMapping("/product/manage/{id}")
    public ResponseEntity<String> showProduct(@PathVariable Long id) {
        try {
            Product product = productService.findProductById(id);
            System.out.println(product);
            JSONObject JSONproduct = new JSONObject();
            JSONproduct.put("productID", id);
            JSONproduct.put("productName", product.getProductName());
            JSONproduct.put("aboutProduct", product.getAboutProduct());
            JSONproduct.put("productDetail", product.getProductDetail());
            JSONproduct.put("price", product.getPrice());
            JSONproduct.put("inStock", product.getInStock());
            JSONproduct.put("alreadySold", product.getAlreadySold());
            if (product.getBrand() != null) {
                JSONproduct.put("brand", product.getBrand().getBrandName());
            } else {
                JSONproduct.put("brand", "選擇店家");
            }
            if (product.getProductTag() != null) {
                JSONproduct.put("productTag", product.getProductTag().getTagName());
            } else {
                JSONproduct.put("productTag", "選擇商品標籤");
            }
            return new ResponseEntity<>(JSONproduct.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //獲取店家資訊
    @GetMapping("/product/showBrands")
    public ResponseEntity<List<Brand>> showBrands() {
        try {
            List<Brand> brands = brandService.findAllBrand();
            return new ResponseEntity<>(brands, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //獲取標籤資訊
    @GetMapping("/product/showProductTags")
    public ResponseEntity<List<ProductTag>> showProductTags() {
        try {
            List<ProductTag> pTags = productTagService.findAllProductTag();
            return new ResponseEntity<>(pTags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //新增
    //商品管理頁新增上架商品
    @ResponseBody
    @PostMapping("/product/manage")
    public Long addNewProduct(@RequestBody Product addProduct) {
        Product product = new Product();
        System.out.println("傳進來的資訊: "  + addProduct);
        product.setProductName(addProduct.getProductName());
        product.setAboutProduct(addProduct.getAboutProduct());
        product.setProductDetail(addProduct.getProductDetail());
        product.setPrice(addProduct.getPrice());
        product.setInStock(addProduct.getInStock());
        product.setAlreadySold(0);
        product.setLaunchedTime(new Date());
        System.out.println(product);
        productService.createOrUpdateProduct(product);
        return product.getId();
    }

    //商品管理頁新增上架商品的店家
    @ResponseBody
    @PostMapping("/product/manage/brand/{id}")
    public void addProductBrand(@PathVariable Long id, @RequestBody Long brandId) {
        Product product = productService.findProductById(id);
        System.out.println("讀到的商品: "  + product);
        System.out.println("傳進的店家: "  + brandId);
        Brand brand = brandService.findBrandById(brandId);
        product.setBrand(brand);
        System.out.println(product);
        productService.createOrUpdateProduct(product);
    }

    //商品管理頁新增上架商品的標籤
    @ResponseBody
    @PostMapping("/product/manage/productTag/{id}")
    public void addProductTag(@PathVariable Long id, @RequestBody Long pTagId) {
        Product product = productService.findProductById(id);
        System.out.println("讀到的商品: "  + product);
        System.out.println("傳進的店家: "  + pTagId);
        ProductTag pTag = productTagService.findProductTagById(pTagId);
        product.setProductTag(pTag);
        productService.createOrUpdateProduct(product);
        System.out.println(product);
    }

    //上傳商品照片
    @ResponseBody
    @PostMapping("/product/manage/productImg/{id}")
    public boolean addProductImg(@PathVariable Long id, @RequestParam(value="file") MultipartFile multipartFile) {

        if(!multipartFile.isEmpty()){

            long size = multipartFile.getSize();
            if(size > 1920*1080){
                System.out.println("圖片尺寸過大");
                return false;
            }

            String fileName = id + ".jpg";
            String dir = "src/main/resources/static/images/shop/product";

            FileUploadUtils.saveUploadFile(dir, fileName, multipartFile);
            return true;
        }
        return false;
    }

    //刪除
    @DeleteMapping("/product/manage/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/admin/product";
    }

    //修改
    //修改商品資訊
    @PutMapping("/product/manage/{id}")
    public void updateProductInfo(@PathVariable Long id, @RequestBody Product product) {
        Product productInfo = productService.findProductById(id);

        System.out.println("傳進來的資訊: "  + product);

        if (!"".equals(product.getProductName())) {
            productInfo.setProductName(product.getProductName());
        }
        if (!"".equals(product.getAboutProduct())) {
            productInfo.setAboutProduct(product.getAboutProduct());
        }
        if (!"".equals(product.getProductDetail())) {
            productInfo.setProductDetail(product.getProductDetail());
        }
        if (product.getPrice() != null) {
            productInfo.setPrice(product.getPrice());
        }
        if (product.getInStock() != null) {
            productInfo.setInStock(product.getInStock());
        }
        System.out.println(productInfo);
        productService.createOrUpdateProduct(productInfo);
    }

    //修改商品的店家
    @PutMapping("/product/manage/brand/{id}")
    public void updateProductBrand(@PathVariable Long id, @RequestBody Long brandId) {
        Product product = productService.findProductById(id);
        System.out.println("讀到的商品: "  + product);
        System.out.println("傳進的店家: "  + brandId);
        Brand brand = brandService.findBrandById(brandId);
        product.setBrand(brand);
        System.out.println(product);
        productService.createOrUpdateProduct(product);
    }

    //修改商品的標籤
    @PutMapping("/product/manage/productTag/{id}")
    public void updateProductTag(@PathVariable Long id, @RequestBody Long pTagId) {
        Product product = productService.findProductById(id);
        System.out.println("讀到的商品: "  + product);
        System.out.println("傳進的店家: "  + pTagId);
        ProductTag pTag = productTagService.findProductTagById(pTagId);
        product.setProductTag(pTag);
        productService.createOrUpdateProduct(product);
        System.out.println(product);
    }

    //修改商品照片
    @PutMapping("/product/manage/productImg/{id}")
    public boolean updateProductImg(@PathVariable Long id, @RequestParam(value="file") MultipartFile multipartFile) {

        if(!multipartFile.isEmpty()){

            long size = multipartFile.getSize();
            if(size > 1920*1080){
                System.out.println("圖片尺寸過大");
                return false;
            }

            String fileName = id + ".jpg";
            String dir = "src/main/resources/static/images/shop/product";

            FileUploadUtils.saveUploadFile(dir, fileName, multipartFile);
            return true;
        }
        return false;
    }

}