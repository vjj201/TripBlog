package com.java017.tripblog.service_impl;

import com.java017.tripblog.repository.UserRepository;
import com.java017.tripblog.entity.Intro;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.security.MyUserDetails;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:35
 */

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //獲取當前使用者
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return user.getUser();
    }

    //判斷記住帳號
    public boolean isRememberMeUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        //判斷當前使用者是否是通過
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    //是否完成信箱驗證
    public boolean isisMailVerified(HttpSession session) {
        User user = getCurrentUser();
        User userSession = new User();
        userSession.setId(user.getId());
        userSession.setNickname(user.getNickname());

        //是否完成信箱驗證
        if(user.isMailVerified()) {
            session.setAttribute("user", userSession);
            return true;
        } else {
            userSession.setEmail(user.getEmail());
            session.setAttribute("signup", userSession);
            return false;
        }
    }

    @Override//創建會員
    public boolean createUser(User user) {
        //保存
        Intro intro = new Intro();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIntro(intro);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override//帳號查詢會員資料
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override//編號查詢會員資料
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override//修改會員資料
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
