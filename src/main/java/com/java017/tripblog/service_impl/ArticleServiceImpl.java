package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Collect;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.repository.CollectRepository;
import com.java017.tripblog.repository.RecommendRepository;
import com.java017.tripblog.repository.ReportRepository;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.util.ArticleParam;
import com.java017.tripblog.util.TagEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ReportRepository reportRepository;
    private final CollectRepository collectRepository;
    private final RecommendRepository recommendRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ReportRepository reportRepository, CollectRepository collectRepository, RecommendRepository recommendRepository) {
        this.articleRepository = articleRepository;
        this.reportRepository = reportRepository;
        this.collectRepository = collectRepository;
        this.recommendRepository = recommendRepository;
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
    public ArrayList<Article> findByEnterAddressNameLikeAndSubjectCategory(String enterAddressName, String subject) {
        if (StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)) {
//            如果都沒有填
            return articleRepository.findAll();
        } else if (StringUtils.isEmpty(enterAddressName) && !StringUtils.isEmpty(subject)) {
//            只有填主題(subject)
            return articleRepository.findBySubjectCategory(subject);
        } else if (!StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)) {
//            只有填搜尋吧(enterAddressName)
            return articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName, enterAddressName, enterAddressName, enterAddressName);

        } else {
            return articleRepository.findBySubjectCategoryOrEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName, enterAddressName, enterAddressName, enterAddressName, subject);
        }

    }

    //庭妤:    文章首頁&文章換頁
    @Override
    public List<Article> getPagedArticles(int page, int size, String enterAddressName, String subject, int timeDirect) {

        //預設-時間舊到新
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending().and(Sort.by("createTime")).ascending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
        System.out.println("實作serverce裡面的排序" + timeDirect);
        //時間新到舊
        if (100 == timeDirect) {
            System.out.println("desc有抓到[IF新到舊]");
            pageable = PageRequest.of(page, size, Sort.by("createDate").descending().and(Sort.by("createTime")).descending().and(Sort.by("subjectCategory")).and(Sort.by("enterAddressName")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
        }

        Page<Article> pageResult;

        if (StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)) {
//            如果都沒有填
            pageResult = articleRepository.findAll(pageable);

        } else if (StringUtils.isEmpty(enterAddressName) && !StringUtils.isEmpty(subject)) {
//            只有填主題(subject)
            pageResult = articleRepository.findBySubjectCategory(subject, pageable);
        } else if (!StringUtils.isEmpty(enterAddressName) && StringUtils.isEmpty(subject)) {
//            只有填搜尋吧(enterAddressName)
            pageResult = articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName, enterAddressName, enterAddressName, enterAddressName, pageable);
        } else {
//            都有填
            List<Article> A = articleRepository.findByEnterAddressNameContainingOrArticleTitleContainingOrTextEditorContainingOrFreeTagContaining(enterAddressName, enterAddressName, enterAddressName, enterAddressName);
            List<Article> newA = new ArrayList<>();
            for (Article loopdata : A) {
                if (subject.equals(loopdata.getSubjectCategory())) {
                    newA.add(loopdata);
                }
            }
            pageResult = new PageImpl<>(newA, pageable, newA.size());

        }


        List<Article> messageList = pageResult.getContent();

        return messageList;

    }


    //    庭妤:      主題_物件陣列
    @Override
    public ArrayList<Article> findBySubjectCategory(String subject) {
        return articleRepository.findBySubjectCategory(subject);
    }

    @Override
    public Article findByArticleTitle(String articleTitle) {
        Article result = articleRepository.findByArticleTitle(articleTitle);
        return result;
    }


    @Override
    public String updateRecommend(String articleTitle) {
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer Recommend = result.getRecommend();
        Recommend++;
        result.setRecommend(Recommend);
        articleRepository.save(result);
        return "推薦成功";
    }



    @Override
    public String updateCollect(String articleTitle) {
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer collect = result.getCollect();
        collect++;
        result.setCollect(collect);
        articleRepository.save(result);
        return "收藏成功";
    }



    @Override
    public String updateReport(String articleTitle) {
        Article result = articleRepository.findByArticleTitle(articleTitle);
        Integer Report = result.getReport();
        Report++;
        result.setReport(Report);
        articleRepository.save(result);
        return "收藏成功";
    }


    @Override
    public ArrayList<Article> findByRandomArticle() {
        ArrayList<Article> list = articleRepository.findAll();
        List<Article> resultList = new ArrayList<>();
        int listSize = list.size();
        System.out.println(listSize);
        for (int i = 0; i < listSize; i++) {
            Article article = new Article();
            Integer math = (int) (Math.random() * listSize) + 1;
            System.out.println("Math" + math);
            Optional<Article> OpArticle = articleRepository.findById(math);
            if (OpArticle.isPresent()) {
                System.out.println("OpArticle" + OpArticle.get().getArticleTitle());
                article.setEnterAddressLat(OpArticle.get().getEnterAddressLat());
                article.setEnterAddressLng(OpArticle.get().getEnterAddressLng());
                article.setArticleTitle(OpArticle.get().getArticleTitle());
                resultList.add(article);
                System.out.println("For裡面" + resultList);
            } else {
                System.out.println("空的");
            }
        }
        System.out.println("server回傳" + resultList);
        return (ArrayList<Article>) resultList;

    }



    public ArrayList<Article> findUserById(User id) {
        if (id == null)
        {
            throw new NullPointerException();
        }
        if (id.getId() <=0)
        {
            return null;
        }

        return articleRepository.findByUserId(id);
    }

    @Override
    public ArrayList<Article> findByUserIdForPage(User id) {
        ArrayList<Article> result = articleRepository.findByUserId(id);
        return result;
    }

    //大方：  文章換頁按鈕自動生成
    @Override
    public ArrayList<Article> findByUserIdAndSubjectCategoryForPage(User id, String subject) {
        ArrayList<Article> result = articleRepository.findByUserId(id);

        if (StringUtils.isEmpty(subject)) {
            return result;
        }
            // 有填主題(subject)
        return articleRepository.findByUserIdAndSubjectCategory(id, subject);

    }

    //大方:  我的空間 - 文章首頁&文章換頁
    @Override
    public List<Article> getMyPagedArticles(int page, int size, Long id, String subject, int timeDirect) {

        System.out.println("articlaserviceimpl getMyPagedArticles" + id);

        //預設-時間舊到新
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by("createDate").ascending()
                        .and(Sort.by("createTime")).ascending()
                        .and(Sort.by("subjectCategory"))
                        .and(Sort.by("articleTitle"))
                        .and(Sort.by("textEditor"))
                        .and(Sort.by("freeTag")));


        System.out.println("實作service的排序" + timeDirect);

        //時間新到舊
        if (timeDirect == 100) {
            System.out.println("desc有抓到(if 新到舊)");
            pageable = PageRequest.of(page,
                    size,
                    Sort.by("createDate").descending().and(Sort.by("createTime")).descending().and(Sort.by("subjectCategory")).and(Sort.by("articleTitle")).and(Sort.by("textEditor")).and(Sort.by("freeTag")));
        }

        Page<Article> pageResult;

        if (!StringUtils.isEmpty(subject)) {
            System.out.println("subject" + subject);
            pageResult = articleRepository.findByUserId_IdAndSubjectCategory(id, subject, pageable);
        } else {
            pageResult = articleRepository.findByUserId_Id(id, pageable);
        }

        List<Article> messageList = pageResult.getContent();


        System.out.println("ArticleServiceImpl的 messageList" + messageList);

        return messageList;
    }


    //大方:    我的空間 - 刪除文章
    public String deleteMyArticle(String articleId) {
        System.out.println(articleId);

        Integer articleIdNum = Integer.parseInt(articleId);
        articleRepository.deleteByArticleId(articleIdNum);

        Article article = new Article();
        article.setArticleId(articleIdNum);
        reportRepository.deleteByArticleId(article);
        collectRepository.deleteByArticleId(article);
        recommendRepository.deleteByArticleId(article);

        System.out.println("執行刪除文章");
        return "文章刪除(Service)";
    }


    @Override
    public ArrayList<Article> findAll() {
        return null;
    }

    @Override
    public String upDateArticle(Article inputArticle) {
        Optional<Article> article = articleRepository.findById(inputArticle.getArticleId());
        inputArticle.setArticleId(article.get().getArticleId());

        articleRepository.save(inputArticle);
        return "ok";
    }


    //從collect找出文章
    @Override
    public List<Collect> findCollectByUser(int page, int size, User userId, String subject, int timeDirect) {

        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by("articlesCollectId_createDate").ascending().and(Sort.by("articlesCollectId_createTime")).ascending());

        if (timeDirect == 100) {
            pageable = PageRequest.of(page,
                    size,
                    Sort.by("articlesCollectId_createDate").descending().and(Sort.by("articlesCollectId_createTime")).descending());
        }
        Page<Collect> pageResult;

        if (!StringUtils.isEmpty(subject)) {
            pageResult = collectRepository.findByUserCollectIdAndArticlesCollectId_SubjectCategory(userId, subject, pageable);
        } else {
            pageResult = collectRepository.findByUserCollectId(userId, pageable);
        }
        System.out.println("pageResult" + pageResult);
        List<Collect> messageList = pageResult.getContent();
        return messageList;
    }

    //我的文章換頁按鈕生成
    @Override
    public List<Collect> findCollectByUserCollectForPage(User id, String subject) {
        List<Collect> result = collectRepository.findByUserCollectId(id);
        if (StringUtils.isEmpty(subject)) {
            return result;
        } else {
            // 有填主題(subject)
            result = collectRepository.findByUserCollectIdAndArticlesCollectId_SubjectCategory(id, subject);
            return result;
        }
    }

    @Override
    public List<Article> findArticleIdArray(Integer id) {
        List<Article> result = articleRepository.findArticleIdArray(id);
        return result;
    }

    //庭妤: 檢舉文章_文章id搜尋，結果為list
    @Override
    public List<Article> findArticleIdReport() {
        ArrayList<Article> result = articleRepository.findAll();
        List<Article> reportArticles = result.stream().filter(m->m.getReport()!=0).collect(Collectors.toList());

        List<Article> newResult = new ArrayList<>();
        newResult.addAll(reportArticles);

        return newResult;
    }


    @Override
    public Article findArticleById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMyCollect(User userId, Article articleId) {
        System.out.println("刪除收藏表格前面");
        collectRepository.deleteArticlesCollectIdAndUserCollectId(articleId,userId);
        Optional<Article> article = articleRepository.findById(articleId.getArticleId());
        int collect = article.get().getCollect();
        collect--;
        article.get().setCollect(collect);
        System.out.println("存回文章前面");
        articleRepository.save(article.get());
    }

    public List<Article> changeImg(){
        List<Article> result =articleRepository.findAll();
        return result;
    }




}

