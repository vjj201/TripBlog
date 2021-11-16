package com.java017.tripblog.service;

import com.java017.tripblog.entity.Report;
import com.java017.tripblog.entity.User;


import java.util.ArrayList;

public interface ReportService {
    Report updateUserReport(Report report);
    //庭妤    顯示[已推薦]
    ArrayList<Report> findByuserReportId(User userReportId);
}
