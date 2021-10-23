package com.java017.tripblog.interceptor;

import com.java017.tripblog.security.MyUserDetails;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YuCheng
 * @date 2021/9/28 - 下午 12:11
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("信箱驗證檢查");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
           if(authentication.isAuthenticated() || RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
               if(!"anonymousUser".equals(authentication.getPrincipal())) {
                   MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
                   //確認
                   if (!myUserDetails.isEnabled()) {
                       System.out.println("帳號未完成信箱驗證");
                       response.sendRedirect("/user/signup-success");
                   }
               }
           }
        }

        return true;
    }

}
