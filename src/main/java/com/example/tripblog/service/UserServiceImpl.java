package com.example.tripblog.service;

import com.example.tripblog.dao.InitializationVectorRepository;
import com.example.tripblog.dao.UserRepository;
import com.example.tripblog.entity.InitializationVector;
import com.example.tripblog.entity.Intro;
import com.example.tripblog.entity.User;
import com.example.tripblog.util.CipherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * @author YuCheng
 * @date 2021/9/27 - 下午 02:35
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final String KEY = "TravelAndEatBlog";
    private SecureRandom secureRandom = new SecureRandom();

    @Override//確認用戶帳密
    public User checkUser(String account, String password) {
        //帳號查詢取得向量值
        User user = userRepository.findByAccount(account);
        if(user == null) {
            return null;
        }

        try {
            password = CipherUtils.encryptString(KEY, password, user.getIv().getIv());
        } catch (Exception e) {
            e.printStackTrace();
        }

        user = userRepository.findByAccountAndPassword(account, password);
        return user;
    }

    @Override//創建會員
    public User createUser(User user) {

        //加密
        String encrypt = "";
        //隨機向量產生
        byte[] iv = new byte[128 / 8];
        secureRandom.nextBytes(iv);
        InitializationVector vector = new InitializationVector();
        vector.setIv(iv);
        Intro intro = new Intro();

        try {
            encrypt = CipherUtils.encryptString(KEY, user.getPassword(), iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (byte i : iv) {
            System.out.print(i + " ");
        }
        System.out.println(encrypt);

        //保存
        user.setPassword(encrypt);
        user.setIv(vector);
        user.setIntro(intro);
        return userRepository.save(user);
    }

    @Override//帳號查詢會員資料
    public User showUserData(String account) {
        return userRepository.findByAccount(account);
    }

    @Override//編號查詢會員資料
    public User showUserData(Long id) {
        User user = userRepository.findById(id).orElse(null);

//        //解密
//        String encrypt = user.getPassword();
//        String decrypt = "";
//        byte[] iv = user.getIv().getIv();
//
//        try {
//            decrypt = CipherUtils.decryptString(KEY, encrypt, iv);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        user.setPassword(decrypt);
        return user;
    }

    @Override//修改會員資料
    public User editorUserData(User user) {
        return userRepository.save(user);
    }
}
