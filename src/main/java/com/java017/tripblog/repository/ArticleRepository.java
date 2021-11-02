package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
//全部
   ArrayList<Article>findAll();
//全頁
//     Page<Article>findAll(Pageable pageable);

//map_search:單_(搜尋吧.地址)
     @Query(value = "select t from Article t where t.enterAddressName like %?1%")
     ArrayList<Article> findByEnterAddressNameLike(String enterAddressName);
//map_search:單_(搜尋吧.地址)_換頁
     @Query(value = "select t from Article t where t.enterAddressName like %?1%")
     Page<Article> findByEnterAddressNamelike(String address, Pageable pageable);


//預設(無篩選)_user_eat&travel換頁
//map_search:單_(下拉.主題)
     ArrayList<Article> findBySubjectCategory(String subject);
//map_search:單_(下拉.主題)_換頁
     Page<Article> findBySubjectCategory(String subject,Pageable pageable);


//map_search:雙_(搜尋吧.地址)+(下拉.主題)
     ArrayList<Article> findByEnterAddressNameAndSubjectCategory(String enterAddressName,String subject);
//map_search:雙_(搜尋吧.地址)+(下拉.主題)_換頁
     Page<Article> findByEnterAddressNameAndSubjectCategory(String address,String subject, Pageable pageable);

     Article findByArticleTitle(String articleTitle);

//map_search:
ArrayList<Article>findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName,String subject);


}

