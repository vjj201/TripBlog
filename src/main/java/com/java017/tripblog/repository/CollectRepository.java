package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Collect;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface CollectRepository extends JpaRepository<Collect,Integer> {

    boolean existsByUserCollectIdAndArticlesCollectId(User collectId, Article articleId);

    ArrayList<Collect> findByUserCollectId(User userCollectId);

    @Modifying
    @Transactional
    @Query("delete from Collect where articlesCollectId = ?1")
    void deleteByArticleId(Article id);
}
