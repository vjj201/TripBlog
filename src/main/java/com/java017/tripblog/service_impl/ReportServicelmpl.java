package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.Report;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ReportRepository;
import com.java017.tripblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ReportServicelmpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
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

//---------------------------------------------------------------
//    @Override
//    public List<Report> findAllReport() {}
//
//    public List<Report> findAllReport() {
//
//        return reportRepository.findAll();}
//
//    --------------------------------------------------------------
//List<Report> All = reportRepository.findAll();
//    List<Report> newAll =  new ArrayList<>();
//    //SET存入不重複的值
//    Set<Integer> repeatIdSet = new HashSet<Integer>();
//    Object[] repeatIdArray = repeatIdSet.toArray();
////        ArrayList<Report> repeatIdList = new ArrayList<>();
//
//        for (int i =1;i< All.size();i++) {
//        Integer articleId = All.get(i-1).getArticlesReportId().getArticleId();
//        Integer articleIdNew = All.get(i).getArticlesReportId().getArticleId();
//        if (articleId != articleIdNew) {
//            newAll.add(All.get(i-1));
//            System.out.println("All.get(i)=>"+All.get(i-1));
//        }else {
//            repeatIdSet.add(articleId);
//        }
//    }
//    //將set-repeatIdSet裝入List-repeatIdList
////        for(int j = 0;j<repeatIdArray.length;j++){
////            repeatIdList[j] =(List<Report>)repeatIdArray[j];
////        }
//
//
//    //
//        for(int k =0;k< All.size();k++){
//        Integer articleId2 =All.get(k).getArticlesReportId().getArticleId();
//        for( int r=0;r<repeatIdSet.size();r++){
//            Integer repeatId = (Integer) repeatIdArray[r];
//            if (articleId2 == repeatId){
////                    newAll.add(All.get(k));
////                    應該是article屬性，然後用get存入資料
//            }
//        }
//
//
//    }
//
//          return newAll;
//
//}
