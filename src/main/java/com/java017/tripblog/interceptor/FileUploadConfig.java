package com.java017.tripblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解決圖片需要重啟才能顯示的問題
@Configuration
public class FileUploadConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //獲取文件真實路徑 /Users/leepeishan/TripBlog/src/main/resources/static/images/
//        String path = System.getProperty("user.dir")+"/src/main/resources/static/images/imgTest/";
        //添加映射
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+ "/Users/leepeishan/TripBlog/src/main/resources/static/images/");
    }
}
