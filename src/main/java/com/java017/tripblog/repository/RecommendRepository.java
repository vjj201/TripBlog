package com.java017.tripblog.repository;



import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Recommend;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface RecommendRepository extends JpaRepository<Recommend,Integer> {
     boolean existsByUserRecommendIdAndArticlesRecommendId(User recommendUserId, Article articleId);

     ArrayList<Recommend> findByUserRecommendId(User userRecommendId);

     @Modifying
     @Transactional
     @Query("delete from Recommend where articlesRecommendId = ?1")
     void deleteByArticleId(Article id);




}
