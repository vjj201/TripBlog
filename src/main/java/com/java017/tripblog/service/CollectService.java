package com.java017.tripblog.service;

import com.java017.tripblog.entity.Collect;
import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;

import java.util.ArrayList;

public interface CollectService {
    // 使用者按讚 by 大方
    Collect updateUserCollect(Collect collect);
    //庭妤    顯示[已推薦]
    ArrayList<Collect> findByuserCollectId(User userRecommendId);

}
