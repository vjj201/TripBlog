package com.example.tripblog.service;

import com.example.tripblog.dao.IntroRepository;
import com.example.tripblog.entity.Intro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sandy
 * @date
 */

@Service
public class IntroServiceImpl implements IntroService {

    @Autowired
    private IntroRepository introRepository;

    @Override//更新自我介紹頁面資訊
    public Intro editIntro(Intro intro) {
        return introRepository.save(intro);
    }

    @Override
    public Intro showIntroData(Long id) {
        Intro intro =  introRepository.findById(id).orElse(null);
        return intro;
    }
}
