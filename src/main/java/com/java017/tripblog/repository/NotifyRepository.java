package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/20 - 下午 03:43
 */
public interface NotifyRepository extends JpaRepository<Notify, Long> {
    List<Notify> findAllByUsername(String username);

    Long countAllByUsernameAndAlreadyRead(String username, boolean alreadyRead);
}
