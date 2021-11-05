package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        return articleRepository.findByEnterAddressNameContaining(enterAddressName);
    }

    //map_search:換頁按鈕自動生成
    @Override
    public ArrayList<Article>findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName,String subject) {

        if(enterAddressName==""){

        if(subject != ""){
            return articleRepository.findBySubjectCategory(subject);
        }
            return articleRepository.findAll();
        }
        //主題一定沒填
        if(subject==""){
            return articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName,enterAddressName, enterAddressName,enterAddressName);
        }
        return articleRepository.findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName,enterAddressName,enterAddressName,enterAddressName,subject);
    }


    //map_search:文章首頁&文章換頁
    @Override
    public List<Article> getPagedArticles(int page, int size, String enterAddressName,String subject,int timeDirect) {

        //預設-時間舊到新
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));

        //時間新到舊
        if(timeDirect==100){
            System.out.println("desc有抓到[IF新到舊]");
            pageable = PageRequest.of(page, size, Sort.by("createDate").descending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
        }


        Page<Article> pageResult;

        if(StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)){
//            如果都沒有填
            pageResult = articleRepository.findAll(pageable);
        }else if(StringUtils.isEmpty(enterAddressName) && !StringUtils.isEmpty(subject)){
//            只有填主題(subject)
            pageResult = articleRepository.findBySubjectCategory(subject,pageable);
        }else if(!StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)) {
//            只有填搜尋吧(enterAddressName)
            pageResult = articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName, enterAddressName,enterAddressName,enterAddressName, pageable);
        }else{
//            都有填
            List<Article> A =articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName,enterAddressName,enterAddressName,enterAddressName);
            List<Article> newA = new ArrayList<>();
            for(Article loopdata:A){
                if(subject.equals(loopdata.getSubjectCategory())){
                    newA.add(loopdata);
                }
            }
            pageResult = new PageImpl<>(newA,pageable,newA.size());

        }


        //(搜尋吧,主題)都有填
                //        Page<Article> pageResult = articleRepository.findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName,enterAddressName,enterAddressName,enterAddressName,subject,pageable);


        //搜尋吧一定沒填
                //        "".equals(enterAddressName)
                //        StringUtils.isEmpty(enterAddressName)
                //        public static boolean isEmpty(@Nullable Object str) {
                //            return (str == null || "".equals(str));}

//        if(enterAddressName == null || "".equals(enterAddressName)){
//            pageResult = articleRepository.findAll(pageable);
//
//            if(subject != null && !"".equals(subject)){
//                pageResult = articleRepository.findBySubjectCategory(subject,pageable);
//            }
//        }
//        //主題一定沒填
//        if(subject == null || "".equals(subject)){
//            pageResult = articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName, enterAddressName,enterAddressName,enterAddressName, pageable);
//        }


        List<Article> messageList =  pageResult.getContent();

        return messageList;

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

    public String updateRecommend(String articleTitle){
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer Recommend = result.getRecommend();
        Recommend ++;
        result.setRecommend(Recommend);
        articleRepository.save(result);
        return "推薦成功";
    };

    public String updateCollect(String articleTitle){
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer collect = result.getCollect();
        collect ++;
        result.setCollect(collect);
        articleRepository.save(result);
        return "收藏成功";
    };

    public String updateReport(String articleTitle){
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer Report = result.getReport();
        Report ++;
        result.setReport(Report);
        articleRepository.save(result);
        return "收藏成功";
    }






}

