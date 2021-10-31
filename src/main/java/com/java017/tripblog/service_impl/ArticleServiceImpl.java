package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public String insertArticle(Article article) {

        articleRepository.save(article);
        return "新增成功";
    }

    @Override
    public ArrayList<Article> findByEnterAddressNameLike(String enterAddressName) {
        return articleRepository.findByEnterAddressNameLike(enterAddressName);
    }

    @Override
    public List<Article> getPagedArticles(int page, int size, String enterAddressName) {
//
        Pageable pageable = PageRequest.of(page, size, Sort.by("enterAddressName").descending()); // 依CREATE_TIME欄位降冪排序
        Page<Article> pageResult = articleRepository.findByEnterAddressNamelike(enterAddressName,pageable);

        pageResult.getNumberOfElements(); // 本頁筆數
        pageResult.getSize();             // 每頁筆數
        pageResult.getTotalElements();    // 全部筆數
        pageResult.getTotalPages();       // 全部頁數

        List<Article> messageList =  pageResult.getContent();

        return messageList;

//  -----------------------------------------------
//    Page<Article> pageResult = articleRepository.findAll(
//
//            PageRequest.of(page,  // 查詢的頁數，從0起算
//                    size, // 查詢的每頁筆數
//                    Sort .by("enterAddress").descending())); // 依CREATE_TIME欄位降冪排序
//
//    pageResult.getNumberOfElements(); // 本頁筆數
//    pageResult.getSize();             // 每頁筆數
//    pageResult.getTotalElements();    // 全部筆數
//    pageResult.getTotalPages();       // 全部頁數
//
//    List<Article> messageList =  pageResult.getContent();
//
//    return messageList;

    }
    //預設(無篩選)_user_eat&travel換頁
    @Override
    public List<Article> getUserEatTravelPagedArticles(int page, int size,String subject) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("subjectCategory").descending());
        Page<Article> pageResult = articleRepository.findBySubjectCategory(subject,pageable);;

        pageResult.getNumberOfElements(); // 本頁筆數
        pageResult.getSize();             // 每頁筆數
        pageResult.getTotalElements();    // 全部筆數
        pageResult.getTotalPages();       // 全部頁數

        List<Article> messageList =  pageResult.getContent();

        return messageList;
    }

    @Override
    public ArrayList<Article> findBySubjectCategory(String subject) {
        return articleRepository.findBySubjectCategory(subject);
    }

    public Article findByArticleTitle(String articleTitle){
      Article result = articleRepository.findByArticleTitle(articleTitle);
      return result;
   }
}

