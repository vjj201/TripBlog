package com.java017.tripblog.service;

import com.java017.tripblog.entity.Report;
import com.java017.tripblog.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface ReportService {
    Report updateUserReport(Report report);
    //庭妤    顯示[已推薦]
    ArrayList<Report> findByUserReportId(User userReportId);

    //庭妤 找到所有檢舉文章
//    List<Report> findAllReport();


}
