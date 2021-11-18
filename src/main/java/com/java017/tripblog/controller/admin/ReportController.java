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
    public ResponseEntity<List<Report>> findAllReport() {
        List<Report> reportList = reportService.findAllReport();
        if (reportList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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


    @PutMapping("/report/{id}")
    public String updateReportById(@PathVariable Integer id, @RequestBody String status) {
        Article report = articleService.findArticleById(id);
        Boolean result;
        if("使用中".equals(status)){
            result = true;
            report.setShow(result);
        }else{
            result = false;
            report.setShow(result);}
        return "成功執行";
//        result.setShow();
//        Brand brandById = brandService.findBrandById(id);
//        if (ObjectUtils.isEmpty(brandById)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        brandById.setBrandName(brand.getBrandName());
//        brandById.setLocation(brand.getLocation());
//        brandById.setAboutBrand(brand.getAboutBrand());
//        return new ResponseEntity<>(HttpStatus.OK);
    }


}
