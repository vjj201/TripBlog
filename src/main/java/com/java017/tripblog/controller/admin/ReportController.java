package com.java017.tripblog.controller.admin;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Report;
import com.java017.tripblog.repository.ReportRepository;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service.ReportService;
import com.java017.tripblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/admin")


public class ReportController {

    private final ReportService reportService;
    private final ArticleService articleService;
    private final UserService userService;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportService reportService, ArticleService articleService, UserService userService, ReportRepository reportRepository) {
        this.reportService = reportService;
        this.articleService = articleService;
        this.userService = userService;
        this.reportRepository = reportRepository;
    }


    @GetMapping("/report")
    public ResponseEntity<List<Article>> findAllReport() {
        List<Article> reportList = articleService.findArticleIdReport();
        if (reportList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println("印出reportList=>"+reportList);
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    //渲染會員文章畫面
    @GetMapping("/report/{articleId}/product")
    public ResponseEntity<List<Article>> articlePage(@PathVariable  Integer articleId) {
        System.out.println(articleId);
        List<Article> result = articleService.findArticleIdArray(articleId);

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //刪除檢舉文章
    @DeleteMapping("/report/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable String id) {
        try {
            articleService.deleteMyArticle(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

   //put封鎖與顯示
    @PutMapping("/report/{id}")
    public String updateReportById(@PathVariable Integer id, @RequestBody String status) {
        System.out.println("status=>"+status);
        String Used = "\"使用中\"";
        Article report = articleService.findArticleById(id);
        Boolean result;
        if( Used.equals(status)){
            result = true;
            report.setShow(result);
            System.out.println("印出[使用中]的getShow()"+report.getShow());
        }else{
            result = false;
            report.setShow(result);
            System.out.println("印出[停用中]的getShow()"+report.getShow());
        }
        return "成功執行";
    }
}
