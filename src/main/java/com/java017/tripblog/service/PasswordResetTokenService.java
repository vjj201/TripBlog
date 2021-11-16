package com.java017.tripblog.service;

import com.java017.tripblog.entity.PasswordResetToken;
import com.java017.tripblog.entity.User;

import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/10/26 - 下午 04:48
 */

public interface PasswordResetTokenService {

    //新增或覆蓋
    void createOrUpdateToken(PasswordResetToken passwordResetToken);

    //根據token尋找
    PasswordResetToken findByToken(String token);

    //根據用戶尋找
    PasswordResetToken findByUser(User user);

    //根據token和日期尋找
    PasswordResetToken findByTokenAndExpiryDate(String token, Date expiryDate);

    //根據token刪除
    void deletePasswordResetToken(Long id);

}
