package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/14 - 下午 02:37
 */

@Transactional
@RestController
@RequestMapping("/admin")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brand")
    public ResponseEntity<List<Brand>> findAllBrand() {
        List<Brand> brandList = brandService.findAllBrand();
        if (brandList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brandList, HttpStatus.OK);
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<Brand> findBrandById(@PathVariable Long id) {
        Brand brand = brandService.findBrandById(id);
        if (ObjectUtils.isEmpty(brand)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @GetMapping("/brand/{id}/product")
    public ResponseEntity<List<Product>> findProductByBrand(@PathVariable Long id) {
        Brand brand = brandService.findBrandById(id);
        if (ObjectUtils.isEmpty(brand)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Product> productList = brand.getProductList();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/brand")
    public ResponseEntity<HttpStatus> createBrand(@RequestBody Brand brand) {
        System.out.println(brand);
        try {
            brandService.createOrUpdateBrand(brand);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/brand/{id}")
    public ResponseEntity<HttpStatus> updateBrandById(@PathVariable Long id, @RequestBody Brand brand) {

        Brand brandById = brandService.findBrandById(id);
        if (ObjectUtils.isEmpty(brandById)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        brandById.setBrandName(brand.getBrandName());
        brandById.setLocation(brand.getLocation());
        brandById.setAboutBrand(brand.getAboutBrand());

        try {
            brandService.createOrUpdateBrand(brandById);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        try {
            brandService.deleteBrandById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
