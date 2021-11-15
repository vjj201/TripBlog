package com.java017.tripblog.service;

import com.java017.tripblog.entity.Brand;
import com.java017.tripblog.entity.Intro;
import com.java017.tripblog.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:05
 */

public interface UserService {

    //獲取當前使用者
    User getCurrentUser();

    //判斷記住帳號
    boolean isRememberMeUser();

    //是否完成信箱驗證
    boolean isMailVerified(HttpSession session);

    //創建會員
    boolean createUser(User user);

    //帳號查詢會員資料
    User findUserByUsername(String username);

    //編號查詢會員資料
    User findUserById(Long id);

    //信箱查詢會員資料
    User findUserByEmail(String email);

    //修改會員資料
    User updateUser(User user);

    //修改會員密碼
    String encodePassword(String newPassword);

    //更新自我介紹
    Intro updateIntro(Intro intro);

    //查詢全部
    List<User> findAllUser();
}
