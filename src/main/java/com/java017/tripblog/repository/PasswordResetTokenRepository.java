package com.java017.tripblog.repository;

import com.java017.tripblog.entity.PasswordResetToken;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/10/26 - 下午 04:55
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByTokenAndExpiryDate(String token, Date expiryDate);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
