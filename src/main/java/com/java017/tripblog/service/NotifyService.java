package com.java017.tripblog.service;

import com.java017.tripblog.entity.Notify;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/20 - 下午 03:46
 */
public interface NotifyService {

    boolean createNotify(Notify notify);

    List<Notify> findAllByUsername(String username);

    void deleteById(Long id);
}
