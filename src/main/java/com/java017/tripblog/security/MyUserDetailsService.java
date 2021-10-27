package com.java017.tripblog.security;

import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:33
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("MyUserDetailsService");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("查無帳號:" + username);
        } else {
            System.out.println("返回用戶資訊");
                return new MyUserDetails(user);
        }
    }
}
