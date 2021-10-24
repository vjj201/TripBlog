package com.java017.tripblog.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:46
 */

@Component
public class MyFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        System.out.println("onAuthenticationFailure");
        System.out.println(e.getMessage());

        if (e.getMessage().equals("User is disabled")) {
            System.out.println("User is disabled");
            return;
        }

        if (e.getMessage().equals("圖形驗證錯誤")) {
            System.out.println("圖形驗證失敗");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        if (e.getMessage().equals("Bad credentials")) {
            System.out.println("帳密錯誤");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
