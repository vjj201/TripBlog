package com.java017.tripblog.controller.user;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Collect;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service.UserService;
import com.java017.tripblog.util.ArticleParam;
import com.java017.tripblog.util.FileUploadUtils;
import com.java017.tripblog.util.TagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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

    //上傳文章圖片
    @ResponseBody
    @PostMapping("/updateArticleImg")
    public boolean updateArticleImg(@RequestParam(value = "file") MultipartFile multipartFile,
                                    HttpSession session) {
        double ma = Math.random() * 100;

        if (!multipartFile.isEmpty()) {

            long size = multipartFile.getSize();
            if (size > 1920 * 1080) {
                System.out.println("圖片尺寸過大");
                return false;
            }

            User user = (User) session.getAttribute("user");

            String fileName = "articleImg.jpg";
            String dir = "target/classes/static/images/" + user.getId() + "/" + ma;

            session.setAttribute("ma", ma);

            FileUploadUtils.saveUploadFile(dir, fileName, multipartFile);

            return true;
        }
        return false;
    }

    //顯示文章圖片
    @RequestMapping(value = "/articlePhoto", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        String dir = "src/main/resources/static/images/" + user.getId() + "/articleImg.jpg";
        File file = new File(dir);
        return Files.readAllBytes(file.toPath());
    }

    @ResponseBody
    @PostMapping("/newArticle")
    public String insert(@RequestBody ArticleParam articleParam, HttpSession session) {
        User user = (User) session.getAttribute("user");
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
        article.setShow(articleParam.isShow());
        article.setSubjectCategory(articleParam.getSubjectCategory());
        article.setUserId(userService.findUserById(user.getId()));
        Double ma = (Double) session.getAttribute("ma");
        if (ma != null) {
            String saveDir = "images/" + user.getId() + "/" + ma + "/articleImg.jpg";
            article.setSaveImgPath(saveDir);
            session.setAttribute("ma", null);
        }
        System.out.println("存入的資料" + article);
        articleService.insertArticle(article);

        return "ok";
    }


    //大方: 文章自動生成_輸入搜尋吧查詢並送出第一頁 1113更 (原findByUserId
    @ResponseBody
    @GetMapping("/myFirstSearchOfPageEatTravel")
    public List<Article> myFirstSearchOfPage(HttpSession session, @RequestParam String subject, @RequestParam int timeDirect) {

        System.out.println("搜尋吧-subject=" + subject);
        User user = (User) session.getAttribute("user");

        List<Article> messagedList;

        messagedList = articleService.getMyPagedArticles(0, 5, user.getId(), subject, timeDirect);
        System.out.println("搜尋吧-順序timeDirect(myEat)=" + timeDirect);
        System.out.println("搜尋吧-messageList(myEat)=" + messagedList);

        return messagedList;
    }


    //大方： 自動生成換頁按鈕(myEat) 1113更
    @ResponseBody
    @GetMapping("/newPageButtonForUser")
    public Integer newChangePageButton(HttpSession session, @RequestParam String subject) {
        System.out.println("自動生成換頁按鈕subject=" + subject);

        User user = (User) session.getAttribute("user");

        ArrayList<Article> list;
//        list = articleService.findByUserIdForPage(user);
        list = articleService.findByUserIdAndSubjectCategoryForPage(user, subject);
        System.out.println("分頁按鈕" + list);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize / 5);
        return pageMount;
    }

    //大方： 刪除文章 (My eat)
    @GetMapping("/delete/{articleTitle}/{articleId}")
    private String deleteMyArticle(@PathVariable String articleTitle, @PathVariable String articleId) {

        System.out.println("刪除文章標題：" + articleTitle);
        articleService.deleteMyArticle(articleId);
        System.out.println("執行刪除文章ok");
        return "redirect:/user/eat";
    }


    //渲染會員文章畫面
    @GetMapping("/article/{articleTitle}")
    public String articlePage(Model model, @PathVariable String articleTitle) {
        Article showArticle = articleService.findByArticleTitle(articleTitle);
        model.addAttribute("showArticle", showArticle);
        User showUser = userService.getCurrentUser();
        model.addAttribute("showUser", showUser);
        return "article";
    }

    //跳轉編輯文章頁
    @GetMapping("/edit/{articleTitle}")
    public String editArticle(Model model, @PathVariable String articleTitle) {
        Article editArticle = articleService.findByArticleTitle(articleTitle);
        model.addAttribute("editArticle", editArticle);
        return "user/edit_article";
    }


    //儲存編輯後的文章
    @ResponseBody
    @PostMapping("/updateArticle")
    public String upDateArticle(@RequestBody Article article, HttpSession session) {
        System.out.println(article.getArticleTitle());
        User user = (User) session.getAttribute("user");
        Double ma = (Double) session.getAttribute("ma");
        article.setUserId(userService.findUserById(user.getId()));

        if (ma != null) {
            String saveDir = "images/" + user.getId() + "/" + ma + "/articleImg.jpg";
            article.setSaveImgPath(saveDir);
            session.setAttribute("ma", null);
        }
        articleService.upDateArticle(article);
        return "編輯成功";
    }

    //第一頁產生文章
    @ResponseBody
    @GetMapping("/myFirstSearchOfPageEatTravelForCollect")
    public List<Collect> myFirstSearchOfPageForCollect(HttpSession session, @RequestParam String subject, @RequestParam int timeDirect) {
        User user = (User) session.getAttribute("user");
        List<Collect> messagedList;
        messagedList = articleService.findCollectByUser(0, 5, user, subject, timeDirect);
        return messagedList;
    }

    //我的會員收藏換頁數字
    @ResponseBody
    @GetMapping("/newPageButtonForCollect")
    public Integer newChangePageButtonForCollect(HttpSession session, @RequestParam String subject) {
        User user = (User) session.getAttribute("user");
        List<Collect> list;
//        list = articleService.findByUserIdForPage(user);
        list = articleService.findCollectByUserCollectForPage(user, subject);
        double listSize = list.size();
        int pageMount = (int) Math.ceil(listSize / 5);
        return pageMount;
    }

    //我的會員收藏改換頁面
    @ResponseBody
    @GetMapping("/myChangeSearchOfPageEatTravelForCollect")
    public List<Collect> myChangeSearchOfPage(HttpSession session, @RequestParam String subject, @RequestParam Integer page, @RequestParam Integer timeDirect) {

        User user = (User) session.getAttribute("user");
        user = userService.findUserById(user.getId());
        List<Collect> messageList;
        messageList = articleService.findCollectByUser(page, 5, user, subject, timeDirect);
        System.out.println("點擊換頁按鈕並換頁的my_messageList=" + messageList);
        return messageList;
    }
    //取消收藏
    @GetMapping("/deleteCollect/{articleId}")
    private String deleteMyCollect(@PathVariable Article articleId,HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("刪除文章標題：" + articleId);
        articleService.deleteMyCollect(user,articleId);
        System.out.println("執行刪除文章ok");
        return "redirect:/user/collection";
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


