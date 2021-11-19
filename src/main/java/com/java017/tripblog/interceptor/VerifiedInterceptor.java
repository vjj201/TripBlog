package com.java017.tripblog.interceptor;

import com.java017.tripblog.entity.User;
import com.java017.tripblog.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author YuCheng
 * @date 2021/9/28 - 下午 12:11
 */

@Component
public class VerifiedInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        boolean mailVerified = false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

            // 確認是否啟用
            if (myUserDetails != null) {
                mailVerified = myUserDetails.isMailVerified();

                if (!mailVerified) {
                    HttpSession session = request.getSession();
                    User userSession = new User();
                    userSession.setId(myUserDetails.getId());
                    userSession.setNickname(myUserDetails.getNickName());
                    userSession.setEmail(myUserDetails.getEmail());
                    session.setAttribute("user", userSession);
                    response.sendRedirect("/user/signup-success");
                }
            }

            if(myUserDetails.isLocked()) {
                response.sendRedirect("/banned");
            }
        }
        return true;
    }

}
