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
//子晉:map_search的map
     ArrayList<Article>  findByEnterAddressNameContaining(String enterAddressName);

     ArrayList<Article> findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String enterAddressName,String articleTitle,String textEditor,String freeTag);

     Page<Article> findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String address, String articleTitle,String textEditor,String freeTag,Pageable pageable);

     Article findByArticleTitle(String articleTitle);
//--------------------------------------------------------------------------
     //全部
     ArrayList<Article>findAll();
     //預設(無篩選)_user_eat&travel換頁
//map_search:單_(下拉.主題)
     ArrayList<Article> findBySubjectCategory(String subject);
     //map_search:單_(下拉.主題)_換頁
     Page<Article> findBySubjectCategory(String subject,Pageable pageable);

     //map_search:雙_(搜尋吧.地址)+(下拉.主題)
     ArrayList<Article> findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String enterAddressName,String articleTitle,String textEditor,String freeTag,String subject);
     //map_search:雙_(搜尋吧.地址)+(下拉.主題)_換頁

     Page<Article> findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String enterAddressName,String articleTitle,String textEditor,String freeTag, String subject,Pageable pageable);

}

