package com.example.tripblog.service;

import com.example.tripblog.entity.Intro;
import com.example.tripblog.entity.User;

/**
 * @author Sandy
 * @date
 */

public interface IntroService {

    //上傳首圖


    //更新自我介紹
    public Intro editIntro(Intro intro);

    //編號查詢會員資料
    public Intro showIntroData(Long id);

}
