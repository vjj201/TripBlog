package com.java017.tripblog.repository;


import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface RecommendRepository extends JpaRepository<Recommend,Integer> {
    public boolean existsByUserRecommendIdAndArticlesRecommendId(User recommendUserId, Article articleId);

//    @EntityGraph(value = "user.id", type = EntityGraph.EntityGraphType.FETCH)
    public ArrayList<Recommend> findByuserRecommendId(User userRecommendId);
}
