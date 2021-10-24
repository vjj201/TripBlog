package com.java017.tripblog.filter;

import com.java017.tripblog.security.MyFailureHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:41
 */
public class BeforeLoginFilter extends OncePerRequestFilter {
    private final MyFailureHandler myFailureHandler = new MyFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("BeforeFilter");
        String uri = request.getRequestURI();
        System.out.println("uri = " + uri);

        //登入阻擋，驗證圖形碼
        if (!"/user/login".equals(uri)) {
            //請求不等於登於直接通過
            filterChain.doFilter(request, response);
        } else {
            System.out.println("驗證帳密前");
            try {
                verifyCode(request);
                filterChain.doFilter(request, response);
            } catch (CaptchaException e) {
                System.out.println("比對失敗:" + e.getMessage());
                myFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
    }

    private void verifyCode(HttpServletRequest request) throws CaptchaException {
        //請求中的圖形驗證
        String imageCode = request.getParameter("imageCode").toUpperCase();
        //會話中的驗證
        HttpSession session = request.getSession();
        String sessionCode = "";
        Object obj = session.getAttribute("captcha");

        if (obj != null) {
            sessionCode = (String) obj;
        }

        if (!ObjectUtils.isEmpty(sessionCode)) {
            //移除會話
            session.removeAttribute("captcha");
        }
        System.out.println("比對驗證碼，imageCode:" + imageCode + " | sessionCode:" + sessionCode);

        //比對
        if (ObjectUtils.isEmpty(sessionCode) || ObjectUtils.isEmpty(imageCode) || !sessionCode.equals(imageCode)) {
            throw new CaptchaException();
        }
    }
}
