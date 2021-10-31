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

     @Query(value = "select t from Article t where t.enterAddressName like %?1%")
     ArrayList<Article> findByEnterAddressNameLike(String enterAddressName);
     @Query(value = "select t from Article t where t.enterAddressName like %?1%")
     Page<Article> findByEnterAddressNamelike(String address, Pageable pageable);
}

