package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.ProductOrderService;
import com.java017.tripblog.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author leepeishan
 * @date 2021/11/16 - 11:27 上午
 */

@Transactional
@RestController
@RequestMapping("/admin")
public class MemberController {
    private final UserService userService;
    private final ProductOrderService productOrderService;

    @Autowired
    public MemberController(UserService userService, ProductOrderService productOrderService) {
        this.userService = userService;
        this.productOrderService = productOrderService;
    }

    // 查詢所有會員
    @GetMapping("/member")
    public ResponseEntity<List<User>> findAllUser() {
        List<User> userList = userService.findAllUser();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // 查詢個別會員詳細資料
    @GetMapping("/member/{id}")
    public ResponseEntity<String> findUserById(@PathVariable Long id) throws JSONException {
        User user = userService.findUserById(id);
        JSONObject JSONproduct = new JSONObject();
        if (user.getNickname() != null) {
            JSONproduct.put("nickname", user.getNickname());
        } else {
            JSONproduct.put("nickname", "未設定");
        }
        if (user.getGender() != null) {
            JSONproduct.put("gender", user.getGender());
        } else {
            JSONproduct.put("gender", "未設定");
        }
        if (user.getBirthday() != null) {
            JSONproduct.put("birthday", user.getBirthday());
        } else {
            JSONproduct.put("birthday", "未設定");
        }
        if (user.getEmail() != null) {
            JSONproduct.put("email", user.getEmail());
        } else {
            JSONproduct.put("email", "未設定");
        }
        if (user.getPhone() != null) {
            JSONproduct.put("phone", user.getPhone());
        } else {
            JSONproduct.put("phone", "未設定");
        }
        JSONproduct.put("signDate",user.getSignDate());
        System.out.println(JSONproduct);
        if (ObjectUtils.isEmpty(user)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(JSONproduct.toString(), HttpStatus.OK);
    }

    // 查詢會員訂單
    @GetMapping("/member/{id}/order")
    public ResponseEntity<List<ProductOrder>> showUserOrder(@PathVariable Long id) {
        User user = userService.findUserById(id);
        List<ProductOrder> userOrderList = productOrderService.findAllByUser(user);
        if (userOrderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userOrderList, HttpStatus.OK);
    }
}



