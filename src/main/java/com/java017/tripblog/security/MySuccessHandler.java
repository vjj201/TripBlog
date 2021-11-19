package com.java017.tripblog.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:48
 */
@Component
public class MySuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            System.out.println("管理員登入完成");
            response.setStatus(HttpServletResponse.SC_CREATED);
            return;
        }
        System.out.println("會員登入完成");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
