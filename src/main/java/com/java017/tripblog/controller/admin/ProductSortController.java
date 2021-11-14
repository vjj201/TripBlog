package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductSort;
import com.java017.tripblog.entity.ProductTag;
import com.java017.tripblog.service.ProductSortService;
import com.java017.tripblog.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/12 - 下午 03:43
 */

@Transactional
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
        System.out.println(productSort);
        try {
            productSortService.createOrUpdateProductSort(productSort);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/productSort/{id}")
    public ResponseEntity<HttpStatus> updateProductSortById(@PathVariable Long id, @RequestBody ProductSort productSort) {
        ProductSort productSortById = productSortService.findProductSortById(id);

        if (ObjectUtils.isEmpty(productSortById)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProductTag> oldList = productSortById.getTagList();
        System.out.println("舊的:" + oldList);
        List<ProductTag> newList = productSort.getTagList();
        oldList.removeAll(newList);
        System.out.println("新的:" + newList);
        System.out.println("要刪除的:" + oldList);

        try {

            for (ProductTag productTag : oldList) {
                List<Product> productList = productTag.getProductList();
                for (int i = 0; i < productList.size(); i++) {
                    productList.get(i).setProductTag(null);
                }
                productTag.setProductList(productList);
            }

            productTagService.deleteByList(oldList);
            productSortById.setSortName(productSort.getSortName());
            productSortById.setTagList(newList);
            productSortService.createOrUpdateProductSort(productSortById);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
