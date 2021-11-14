package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//庭妤:    文章換頁按鈕自動生成
    @Override
    public ArrayList<Article>findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName,String subject) {
        if(StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)){
//            如果都沒有填
            return articleRepository.findAll();
        }else if(StringUtils.isEmpty(enterAddressName) && !StringUtils.isEmpty(subject)){
//            只有填主題(subject)
            return articleRepository.findBySubjectCategory(subject);
        }else if(!StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)) {
//            只有填搜尋吧(enterAddressName)
            return articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName,enterAddressName, enterAddressName,enterAddressName);

       }else{
            return articleRepository.findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName,enterAddressName,enterAddressName,enterAddressName,subject);
        }

    }

//庭妤:    文章首頁&文章換頁
    @Override
    public List<Article> getPagedArticles(int page, int size, String enterAddressName,String subject,int timeDirect) {

        //預設-時間舊到新
        Pageable pageable = PageRequest.of(page, size,Sort.by("createDate").ascending().and(Sort.by("createTime")).ascending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
        System.out.println("實作serverce裡面的排序" + timeDirect);
        //時間新到舊
        if( 100 == timeDirect){
            System.out.println("desc有抓到[IF新到舊]");
            pageable = PageRequest.of(page, size, Sort.by("createDate").descending().and(Sort.by("createTime")).descending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
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


        List<Article> messageList =  pageResult.getContent();

        return messageList;

    }


//    庭妤:      主題_物件陣列
    @Override
    public ArrayList<Article> findBySubjectCategory(String subject) {
        return articleRepository.findBySubjectCategory(subject);
    }

    @Override
   public Article findByArticleTitle(String articleTitle){
      Article result = articleRepository.findByArticleTitle(articleTitle);
      return result;
   }


        @Override
    public String updateRecommend(String articleTitle){
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer Recommend = result.getRecommend();
        Recommend ++;
        result.setRecommend(Recommend);
        articleRepository.save(result);
        return "推薦成功";
    };
    @Override
    public String updateCollect(String articleTitle){
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer collect = result.getCollect();
        collect ++;
        result.setCollect(collect);
        articleRepository.save(result);
        return "收藏成功";
    };
    @Override
    public String updateReport(String articleTitle){
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer Report = result.getReport();
        Report ++;
        result.setReport(Report);
        articleRepository.save(result);
        return "收藏成功";
    }



    @Override
    public ArrayList<Article> findByRandomArticle(){
        ArrayList<Article> list = articleRepository.findAll();
        Optional<Article> OpArticle;
        List<Article> resultList = new ArrayList<>();
        int listSize =  list.size();
        System.out.println(listSize);
        for(int i = 0; i<listSize ; i++){
            Article article = new Article();
            Integer math = (int)(Math.random()*listSize)+1;
            System.out.println("Math" + math);
            OpArticle = articleRepository.findById(math);
            article.setEnterAddressLat(OpArticle.get().getEnterAddressLat());
            article.setEnterAddressLng(OpArticle.get().getEnterAddressLng());
            article.setArticleTitle(OpArticle.get().getArticleTitle());
            resultList.add(article);
            System.out.println("For裡面"+ resultList);
        }
        System.out.println("server回傳" +resultList );
        return (ArrayList<Article>) resultList;

    }

    @Override
    public ArrayList<Article> findUserById(User id) {
        ArrayList<Article> result = articleRepository.findByUserId(id);
        return result;
    }

    @Override
    public ArrayList<Article> findByUserIdForPage(User id){
        ArrayList<Article> result = articleRepository.findByUserId(id);
        return result;
    }

    //大方：  文章換頁按鈕自動生成
    @Override
    public ArrayList<Article> findByUserIdAndSubjectCategoryForPage(User id, String subject){
        ArrayList<Article> result = articleRepository.findByUserId(id);

        if(StringUtils.isEmpty(subject)){
            return result;
        }else{
            // 有填主題(subject)
            return articleRepository.findByUserIdAndSubjectCategory(id,subject);
        }
    }

    //大方:  我的空間 - 文章首頁&文章換頁
    @Override
    public List<Article> getMyPagedArticles(int page, int size, Long id, String subject, int timeDirect) {

        System.out.println("articlaserviceimpl getMyPagedArticles"+ id);

        //預設-時間舊到新
        Pageable pageable = PageRequest.of(page,
                                           size,
                                           Sort.by("createDate").ascending().and(Sort.by("createTime")).ascending().and(Sort.by("subjectCategory")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));


        System.out.println("實作service的排序" + timeDirect);

        //時間新到舊
        if(timeDirect == 100){
            System.out.println("desc有抓到(if 新到舊)");
            pageable = PageRequest.of(page,
                                      size,
                                      Sort.by("createDate").descending().and(Sort.by("createTime")).descending().and(Sort.by("subjectCategory")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
        }

        Page<Article> pageResult;

        if(!StringUtils.isEmpty(subject)){
            System.out.println("subject" + subject);
            pageResult = articleRepository.findByUserId_IdAndSubjectCategory(id,subject,pageable);
        }else {
            pageResult = articleRepository.findByUserId_Id(id, pageable);
        }

        List<Article> messageList = pageResult.getContent();


        System.out.println("ArticleServiceImpl的 messageList" + messageList);

        return messageList;
    }


    //大方:    我的空間 - 刪除文章
    public String deleteMyArticle(String articleTitle){

        articleRepository.deleteArticleByArticleTitle(articleTitle);

        return "文章刪除(Service)";
    }

    @Override
    public ArrayList<Article> findAll() {
        return null;
    }

    @Override
    public String upDateArticle(Article inputArticle) {
        Optional<Article> article = articleRepository.findById(inputArticle.getArticleId());
        Article article1 = new Article();
        article1.setArticleId(article.get().getArticleId());
        article1.setArticleTitle(inputArticle.getArticleTitle());
        article1.setSubjectCategory(inputArticle.getSubjectCategory());
        article1.setEnterAddressLat(inputArticle.getEnterAddressLat());
        article1.setEnterAddressLng(inputArticle.getEnterAddressLng());
        article1.setEnterAddressName(inputArticle.getEnterAddressName());
        article1.setSaveImgPath(inputArticle.getSaveImgPath());
        article1.setTextEditor(inputArticle.getTextEditor());
        article1.setFreeTag(inputArticle.getFreeTag());
        article1.setCreateTime(inputArticle.getCreateTime());
        article1.setUserId(inputArticle.getUserId());
        articleRepository.save(article1);
        return "ok";
    }

//    @Override
//    public List<Article> getPagedArticlesId(int page, int size, User user,String subject,int timeDirect) {
//
//        //預設-時間舊到新
//        Pageable pageable = PageRequest.of(page, size,Sort.by("createDate").ascending().and(Sort.by("createTime")).ascending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
//        System.out.println("實作serverce裡面的排序" + timeDirect);
//        //時間新到舊
//        if( 100 == timeDirect){
//            System.out.println("desc有抓到[IF新到舊]");
//            pageable = PageRequest.of(page, size, Sort.by("createDate").descending().and(Sort.by("createTime")).descending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
//        }
//
//        Page<Article> pageResult;
//
//        if(StringUtils.isEmpty(subject)){
//            pageResult = articleRepository.findByUserId(user);
//        }else {
//            pageResult = articleRepository.findByUserIdAndSubjectCategory(user,subject);
//        }
//
//        Page<Article> pageResult;
//        return messageList;
//
//    }

}

