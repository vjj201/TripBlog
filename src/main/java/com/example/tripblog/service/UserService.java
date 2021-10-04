package com.example.tripblog.service;

import com.example.tripblog.entity.User;

/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:05
 */

public interface UserService {

    //確認用戶帳密
    public User checkUser(String account, String password);

    //創建會員
    public User createUser(User user);

    //帳號查詢會員資料
    public User showUserData(String account);

    //編號查詢會員資料
    public User showUserData(Long id);

    //修改會員資料
    public User editorUserData(User user);

}