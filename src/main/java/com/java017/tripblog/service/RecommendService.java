package com.java017.tripblog.service;

import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;

import java.util.ArrayList;

public interface RecommendService {
    // 使用者按讚 by 大方
    Recommend updateUserRecommend(Recommend recommend);
    //庭妤    顯示[已推薦]
    ArrayList<Recommend> findByuserRecommendId(User userRecommendId);


}
