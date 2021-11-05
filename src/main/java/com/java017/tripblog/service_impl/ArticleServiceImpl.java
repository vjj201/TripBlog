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

    //map_search:換頁按鈕自動生成
    @Override
    public ArrayList<Article>findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName,String subject) {

        if(enterAddressName==""){
            return articleRepository.findAll();
        }
        if(subject != ""){
            return articleRepository.findBySubjectCategory(subject);
        }
        //主題一定沒填
        if(subject==""){
            return articleRepository.findByEnterAddressNameContaining(enterAddressName);
        }
        return articleRepository.findByEnterAddressNameContainingAndSubjectCategory(enterAddressName,subject);
    }


//    @Override
//    public List<Article> getPagedArticles(int page, int size, String enterAddressName) {
////
//        Pageable pageable = PageRequest.of(page, size, Sort.by("enterAddressName").descending()); // 依CREATE_TIME欄位降冪排序
//        Page<Article> pageResult = articleRepository.findByEnterAddressNamelike(enterAddressName,pageable);
//
//        pageResult.getNumberOfElements(); // 本頁筆數
//        pageResult.getSize();             // 每頁筆數
//        pageResult.getTotalElements();    // 全部筆數
//        pageResult.getTotalPages();       // 全部頁數
//
//        List<Article> messageList =  pageResult.getContent();
//
//        return messageList;
//
//    }

    //map_search:文章首頁&文章換頁
    @Override
    public List<Article> getPagedArticles(int page, int size, String enterAddressName,String subject,int timeDirect) {

        System.out.println("上，Service-timeDirect="+timeDirect);
        //預設-時間舊到新
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")));

        System.out.println("下，Service-timeDirect="+timeDirect);

        System.out.println("asc有抓到[預設排序(舊到新)]");


        //時間新到舊
        if(timeDirect==100){
            System.out.println("desc有抓到[IF新到舊]");
            pageable = PageRequest.of(page, size, Sort.by("createDate").descending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")));
        }

        //(搜尋吧,主題)都有填
        Page<Article> pageResult = articleRepository.findByEnterAddressNameContainingAndSubjectCategory(enterAddressName,subject,pageable);


        //搜尋吧一定沒填
        if(enterAddressName==""){
            pageResult = articleRepository.findAll(pageable);

            if(subject != ""){
                pageResult = articleRepository.findBySubjectCategory(subject,pageable);
            }
        }
        //主題一定沒填
        if(subject==""){
            pageResult = articleRepository.findByEnterAddressNameContaining(enterAddressName,pageable);
        }



        pageResult.getNumberOfElements(); // 本頁筆數
        pageResult.getSize();             // 每頁筆數
        pageResult.getTotalElements();    // 全部筆數
        pageResult.getTotalPages();       // 全部頁數

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

    };



}
