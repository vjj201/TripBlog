package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Report;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ReportRepository extends JpaRepository< Report,Integer>{
    boolean existsByUserReportIdAndArticlesReportId(User reportId, Article articleId);

    ArrayList<Report> findByUserReportId(User userReportId);

    @Modifying
    @Transactional
    @Query("delete from Report where articlesReportId = ?1")
    void deleteByArticleId(Article id);
}
