package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Collect;
import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CollectRepository extends JpaRepository<Collect,Integer> {

    boolean existsByUserCollectIdAndArticlesCollectId(User collectId, Article articleId);

    ArrayList<Collect> findByUserCollectId(User userRecommendId);
}
