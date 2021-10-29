package com.java017.tripblog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YuCheng
 * @date 2021/9/28 - 下午 12:14
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final HandlerInterceptor loginInterceptor;

    @Autowired
    public WebConfig(HandlerInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).
                addPathPatterns("");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //獲取文件真實路徑 /Users/leepeishan/TripBlog/src/main/resources/static/images/
//        //String path = System.getProperty("user.dir")+"/src/main/resources/static/images/imgTest/";
//        //添加映射
//        registry.addResourceHandler("/images/**").addResourceLocations("file:" + "/Users/samuelahsu/Desktop/專題/TripBlog-master/src/main/resources/static/images/");
//    }
}
