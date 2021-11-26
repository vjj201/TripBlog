package com.java017.tripblog.controller;

import com.java017.tripblog.entity.FreeTag;
import com.java017.tripblog.service.FreeTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class FreetagController {

    private static final Logger log
            = LoggerFactory.getLogger(FreetagController.class);

    private final FreeTagService freeTagService;

    @Autowired
    public FreetagController(FreeTagService freeTagService) {
        this.freeTagService = freeTagService;
    }

    //大方  新增文章畫面tag標籤生成 1117
    @GetMapping("/user/newFindTags")
    public ArrayList<FreeTag> showTags() {

        ArrayList<FreeTag> freeTagName = freeTagService.findAllTags();

        System.out.println("freeTagName名稱" + freeTagName);
        log.info("結束controller");

        return freeTagName;
    }
}
