package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Discount;
import com.java017.tripblog.entity.Product;
import com.java017.tripblog.entity.ProductTag;
import com.java017.tripblog.service.DiscountService;
import com.java017.tripblog.util.FileUploadUtils;
import com.java017.tripblog.vo.ProductQuery;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author leepeishan
 * @date 2021/11/20 - 3:36 下午
 */

@Transactional
@RestController
@RequestMapping("/admin")
public class DiscountController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    //新增
    //優惠管理頁新增優惠
    @ResponseBody
    @PostMapping("/discount")
    public void addNewDiscount(@RequestBody Discount discount) {
        Discount newDiscount = new Discount();
        System.out.println("傳進來的資訊: "  + discount);
        newDiscount.setTitle(discount.getTitle());
        newDiscount.setDetail(discount.getDetail());
        newDiscount.setRequirement(discount.getRequirement());
        newDiscount.setDiscountNumber(discount.getDiscountNumber());
        newDiscount.setCreateDate(new Date());
        newDiscount.setExpiredTime(discount.getExpiredTime());

        discountService.createOrUpdateDiscount(newDiscount);
    }

    // 查詢
    // 查詢所有優惠
    @GetMapping("/discount")
    public ResponseEntity<List<Discount>> showDiscounts() {
        try {
            List<Discount> discounts = discountService.findAllDiscount();
            return new ResponseEntity<>(discounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 查詢單筆優惠
    @GetMapping("/discount/{id}")
    public ResponseEntity<Discount> showDiscount(@PathVariable Long id) {
        try {
            Discount discount = discountService.findDiscountById(id);
            return new ResponseEntity<>(discount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //刪除
    @DeleteMapping("/discount/{id}")
    public ResponseEntity deleteDiscount(@PathVariable Long id){
        discountService.deleteDiscountById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}