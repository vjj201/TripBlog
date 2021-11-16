package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Report;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ReportRepository;
import com.java017.tripblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ReportServicelmpl implements ReportService {

    @Autowired
    private final ReportRepository reportRepository;

    public ReportServicelmpl(ReportRepository repository) {
        this.reportRepository = repository;
    }


    @Override
    public Report updateUserReport(Report report) {
        User reportUserId = report.getUserReportId();
        System.out.println("4你?");
        Article articleId = report.getArticlesReportId();
        System.out.println("4你?");


        if (!reportRepository.existsByUserReportIdAndArticlesReportId(reportUserId, articleId)) { //判斷資料庫是否有重覆資料 還未寫完
            System.out.println("有沒有近來使用者按讚執行成功");
            reportRepository.save(report);
            System.out.println("使用者按讚執行成功");

        } else if (reportRepository.existsByUserReportIdAndArticlesReportId(reportUserId, articleId)) {
            System.out.println("資料庫已有相同資料(recommendUserId+articleId)");
            return null;
        }
        return report;
    }

    @Override
    public ArrayList<Report> findByUserReportId(User userReportId) {
        return reportRepository.findByUserReportId(userReportId);
    }
}
