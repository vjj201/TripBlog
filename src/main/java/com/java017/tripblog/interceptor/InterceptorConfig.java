package com.java017.tripblog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YuCheng
 * @date 2021/9/28 - 下午 12:14
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    private final VerifiedInterceptor verifiedInterceptor;

    @Autowired
    public InterceptorConfig(VerifiedInterceptor verifiedInterceptor) {
        this.verifiedInterceptor = verifiedInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verifiedInterceptor).
                addPathPatterns("/user/write").
                addPathPatterns("/user/space").
                addPathPatterns("/shop/orderList");
    }
}
