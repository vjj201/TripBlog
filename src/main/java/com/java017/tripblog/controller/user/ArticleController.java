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
    @GetMapping("/findtags")
    public TagEnum[] showTags() {
//        List<Freetag> freetag = iFreetag.findAlltag();
//        System.out.println(freetag);
        return TagEnum.values();
    }

    @ResponseBody
    @PostMapping("/newarticle")
    public String insert(@RequestBody ArticleParam articleParam) {

        //    articleRepository.save(article);
        Article article = new Article();
        for (String tag : articleParam.getFree_Tags()) {
            try {
                TagEnum.valueOf(tag);
            } catch (Exception e) {
                System.out.println(tag);
                return "請確認標籤";
            }
        }

        String tag = String.join(",", articleParam.getFree_Tags());
        article.setFree_tag(tag);
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setEnterAddress(articleParam.getEnterAddress());
        article.setSelectRegion(articleParam.getSelectRegion());
        article.setTextEditor(articleParam.getTextEditor());
        article.setSubjectCategory(articleParam.getSubjectCategory());
        articleRepository.save(article);

        return "ok";
    }

    //   public  List< Article> Get(TagEnum tag)
    //   {
    //       String x= "select * from Article where free_tag like ='%{tag}%' ";
    //   }


//    @ResponseBody
//    @PostMapping("/pust")
//    public String showTags(@RequestBody Freetag freetag) {
//        System.out.println(freetag);
//        freeTagRepository.save(freetag);
//        return "1123";
//    }

//    @ResponseBody
//    @PostMapping("/tags")
//    public String insert(@RequestBody Tags tags){
//        System.out.println(tags);
//        tagsRepository.save(tags);
//        return "ya~~~~";

}
