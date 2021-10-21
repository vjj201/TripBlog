package com.java017.tripblog.controller.user;

import com.java017.tripblog.util.ArticleParam;
import com.java017.tripblog.entity.Article;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.util.TagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author YuCheng
 * @date 2021/10/8 - 上午 12:36
 */

@Controller
@RequestMapping("/user")
public class ArticleController {


    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    //跳轉撰寫新文章頁
    @GetMapping("/write")
    public String writePage() {
        return "user/write_article";
    }

    @ResponseBody
    @GetMapping("/findTags")
    public TagEnum[] showTags() {
        return TagEnum.values();
    }

    @ResponseBody
    @PostMapping("/newArticle")
    public String insert(@RequestBody ArticleParam articleParam) {

        //    articleRepository.save(article);
        Article article = new Article();
        for (String tag : articleParam.getFreeTags()) {
            try {
                TagEnum.valueOf(tag);
            } catch (Exception e) {
                System.out.println(tag);
                return "請確認標籤";
            }
        }

        String tag = String.join(",", articleParam.getFreeTags());
        article.setFreeTags(tag);
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setEnterAddress(articleParam.getEnterAddress());
        article.setSelectRegion(articleParam.getSelectRegion());
        article.setTextEditor(articleParam.getTextEditor());
        article.setSubjectCategory(articleParam.getSubjectCategory());
        articleRepository.save(article);

        return "ok";
    }
}
