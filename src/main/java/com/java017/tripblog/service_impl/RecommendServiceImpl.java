package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.RecommendRepository;
import com.java017.tripblog.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RecommendServiceImpl implements RecommendService {

    private final RecommendRepository recommendRepository;

    @Autowired
    public RecommendServiceImpl(RecommendRepository recommendRepository) {
        this.recommendRepository = recommendRepository;
    }


    // 使用者按讚 by 大方
    @Override
    public Recommend updateUserRecommend(Recommend recommend) {
        User recommendUserId = recommend.getUserRecommendId();
        Article articleId = recommend.getArticlesRecommendId();

        if (!recommendRepository.existsByUserRecommendIdAndArticlesRecommendId(recommendUserId, articleId)) { //判斷資料庫是否有重覆資料 還未寫完

            recommendRepository.save(recommend);
            System.out.println("使用者按讚執行成功");

        }else if (recommendRepository.existsByUserRecommendIdAndArticlesRecommendId(recommendUserId, articleId)){
            System.out.println("資料庫已有相同資料(recommendUserId+articleId)");
            return null;
        }
        return recommend;
    }

//庭妤    顯示[已推薦]
    @Override
    public ArrayList<Recommend> findByuserRecommendId(User userRecommendId) {


        return recommendRepository.findByuserRecommendId(userRecommendId);
    }



}
