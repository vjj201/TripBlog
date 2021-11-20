package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Notify;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.NotifyRepository;
import com.java017.tripblog.repository.UserRepository;
import com.java017.tripblog.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/20 - 下午 03:50
 */

@Service
public class NotifyServiceImpl implements NotifyService {

    private final NotifyRepository notifyRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotifyServiceImpl(NotifyRepository notifyRepository, UserRepository userRepository) {
        this.notifyRepository = notifyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean createNotify(Notify notify) {
        User user = userRepository.findByUsername(notify.getUsername());
        if(ObjectUtils.isEmpty(user)) {
            return false;
        }
        notifyRepository.save(notify);
        return true;
    }

    @Override
    public List<Notify> findAllByUsername(String username) {
        return notifyRepository.findAllByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
     notifyRepository.deleteById(id);
    }
}
