package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.ProductSort;
import com.java017.tripblog.service.ProductSortService;
import com.java017.tripblog.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/12 - 下午 03:43
 */

@RestController
@RequestMapping("/admin")
public class ProductSortController {

    private final ProductSortService productSortService;
    private final ProductTagService productTagService;

    @Autowired
    public ProductSortController(ProductSortService productSortService, ProductTagService productTagService) {
        this.productSortService = productSortService;
        this.productTagService = productTagService;
    }

    @GetMapping("/productSort")
    public ResponseEntity<List<ProductSort>> findAllProductSort() {
        List<ProductSort> productSortList = productSortService.findAllProductSort();
        if (productSortList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productSortList, HttpStatus.OK);
    }

    @GetMapping("/productSort/{id}")
    public ResponseEntity<ProductSort> findProductSortById(@PathVariable Long id) {
        ProductSort productSort = productSortService.findProductSortById(id);
        if (ObjectUtils.isEmpty(productSort)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productSort, HttpStatus.OK);
    }

    @PostMapping("/productSort")
    public ResponseEntity<HttpStatus> createProductSort(@RequestBody ProductSort productSort) {
        try {
            System.out.println(productSort);
            productSortService.createOrUpdateProductSort(productSort);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/productSort/{id}")
    public ResponseEntity<ProductSort> updateProductSortById(@PathVariable Long id, @RequestBody ProductSort productSort) {
        ProductSort productSortById = productSortService.findProductSortById(id);
        if (ObjectUtils.isEmpty(productSortById)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productSortById.setSortName(productSort.getSortName());
        productSortById.setTagList(productSort.getTagList());
        return new ResponseEntity<>(productSortService.createOrUpdateProductSort(productSortById), HttpStatus.OK);
    }

    @DeleteMapping("/productSort/{id}")
    public ResponseEntity<HttpStatus> deleteProductSortById(@PathVariable Long id) {
        try {
            productSortService.deleteProductSortById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
