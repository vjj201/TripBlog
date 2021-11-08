package com.java017.tripblog.repository;

import com.java017.tripblog.entity.Article;

import com.java017.tripblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
//子晉:地址_物件陣列_map_search的map
     ArrayList<Article>  findByEnterAddressNameContaining(String enterAddressName);
//庭妤:     搜尋吧_物件陣列
     ArrayList<Article> findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String enterAddressName,String articleTitle,String textEditor,String freeTag);
//庭妤:    搜尋吧_分頁
     Page<Article> findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String address, String articleTitle,String textEditor,String freeTag,Pageable pageable);
//子晉:    標題_物件陣列
     Article findByArticleTitle(String articleTitle);
//庭妤:      全部
     ArrayList<Article>findAll();
//庭妤:      主題_物件陣列
     ArrayList<Article> findBySubjectCategory(String subject);
//庭妤:      主題_分頁
     Page<Article> findBySubjectCategory(String subject,Pageable pageable);

//庭妤:      主題+搜尋吧_物件陣列
     ArrayList<Article> findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(String enterAddressName,String articleTitle,String textEditor,String freeTag,String subject);

     Optional<Article> findById(Integer id);
     ArrayList<Article> findByUserId(Long id);

}

