package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.PasswordResetToken;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.PasswordResetTokenRepository;
import com.java017.tripblog.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/10/26 - 下午 05:08
 */

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {


    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    //新增或覆蓋
    @Override
    public void createOrUpdateToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    //根據token尋找
    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    //根據用戶尋找
    @Override
    public PasswordResetToken findByUser(User user) {
        return passwordResetTokenRepository.findByUser(user);
    }

    //根據token和日期尋找
    @Override
    public PasswordResetToken findByTokenAndExpiryDate(String token, Date expiryDate) {
        return passwordResetTokenRepository.findByTokenAndExpiryDate(token, expiryDate);
    }

    //根據token刪除
    @Override
    public void deletePasswordResetToken(Long id) {
        passwordResetTokenRepository.deleteById(id);
    }
}
