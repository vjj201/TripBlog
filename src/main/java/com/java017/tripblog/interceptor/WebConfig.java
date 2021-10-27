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
//        //添加映射
//        registry.addResourceHandler("/images/**").addResourceLocations("C:/Users/johnn/OneDrive/Documents/back_end/TripBlog/TripBlog/src/main/resources/static/images/");
//    }
}
