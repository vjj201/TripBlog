package com.java017.tripblog.controller.user;

import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service.UserService;
import com.java017.tripblog.service_impl.ArticleServiceImpl;
import com.java017.tripblog.util.ArticleParam;
import com.java017.tripblog.entity.Article;
import com.java017.tripblog.util.FileUploadUtils;
import com.java017.tripblog.util.TagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * @author YuCheng
 * @date 2021/10/8 - 上午 12:36
 */

@Controller
@RequestMapping("/user")
public class ArticleController {



    private final UserService userService;
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService,
                             UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
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

    //上傳文章圖片
    @ResponseBody
    @PostMapping("/updateArticleImg")
    public boolean updateArticleImg(@RequestParam(value="file") MultipartFile multipartFile,
                                    HttpSession session) {

        if(!multipartFile.isEmpty()){

            long size = multipartFile.getSize();
            if(size > 1920*1080){
                System.out.println("圖片尺寸過大");
                return false;
            }

            User user = (User)session.getAttribute("user");

            String fileName = "articleImg.jpg";
            String dir = "src/main/resources/static/images/userArticlePhoto/" + user.getId();

            FileUploadUtils.saveUploadFile(dir, fileName, multipartFile);
            user = userService.findUserById(user.getId());
            user.getIntro().setHasBanner(true);
            userService.updateUser(user);
            return true;
        }
        return false;
    }

    //顯示文章圖片
    @RequestMapping(value = "/articlePhoto", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(HttpSession session) throws IOException {
        User user = (User)session.getAttribute("user");
        String dir = "src/main/resources/static/images/userArticlePhoto/" + user.getId() + "/articleImg.jpg";
        File file = new File(dir);
        return Files.readAllBytes(file.toPath());
    }




    @ResponseBody
    @PostMapping("/newArticle")
    public String insert(@RequestBody ArticleParam articleParam,HttpSession session) {
        User user = (User)session.getAttribute("user");
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
        article.setFreeTag(tag);
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setEnterAddressName(articleParam.getEnterAddressName());
        article.setEnterAddressLng(articleParam.getEnterAddressLng());
        article.setEnterAddressLat(articleParam.getEnterAddressLat());
        article.setSelectRegion(articleParam.getSelectRegion());
        article.setTextEditor(articleParam.getTextEditor());
        article.setSubjectCategory(articleParam.getSubjectCategory());
        article.setUserId(userService.findUserById(user.getId()));
        articleService.insertArticle(article);

        return "ok";
    }

    @ResponseBody
    @GetMapping("/findByUserId")
    public ArrayList<Article> findByUserId( HttpSession session){

        User user = (User)session.getAttribute("user");
        ArrayList<Article> result = articleService.findUserById(user);
        System.out.println("檢查controller 回傳直" + result);
        return result;
    }

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


