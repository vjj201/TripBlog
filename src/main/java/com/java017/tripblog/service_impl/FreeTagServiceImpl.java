package com.java017.tripblog.service_impl;

import com.java017.tripblog.entity.FreeTag;
import com.java017.tripblog.repository.FreeTagRepository;
import com.java017.tripblog.service.FreeTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//大方  新增文章畫面tag標籤生成 1117

@Service
public class FreeTagServiceImpl implements FreeTagService {


    FreeTagRepository freeTagRepository;

    private static final Logger log
            = LoggerFactory.getLogger(FreeTagServiceImpl.class);

    @Autowired
    public FreeTagServiceImpl(FreeTagRepository freeTagRepository) {
        this.freeTagRepository = freeTagRepository;
    }

    public  ArrayList<FreeTag> findAllTags() {
        log.info("findAllTags 進入service層");

        System.out.println(freeTagRepository.findAll());

        ArrayList<FreeTag> freeTagList =new ArrayList<>(freeTagRepository.findAll());
        System.out.println(freeTagList);

        log.info("serviceImpl end");


        return freeTagList;
    }


}