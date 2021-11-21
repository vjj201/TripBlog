package com.java017.tripblog.filter;

import com.java017.tripblog.entity.User;
import com.java017.tripblog.security.MyUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author YuCheng
 * @date 2021/10/22 - 下午 05:22
 */
public class AfterLoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //統一創建登入會話
        if (authentication != null) {
            if (!AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass()) && authentication.isAuthenticated()) {

                HttpSession session = request.getSession();

                if (session.getAttribute("user") == null) {
                    System.out.println("首次進入，創建登入會話");

//                    if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {}
                    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
                    User userSession = new User();
                    userSession.setId(userDetails.getId());
                    userSession.setUsername(userDetails.getUsername());
                    userSession.setNickname(userDetails.getNickName());
                    userSession.setEmail(userDetails.getEmail());
                    userSession.setHasMemberPic(userDetails.hasMemberPic());
                    session.setAttribute("user", userSession);

                }

            }
        }

        filterChain.doFilter(request, response);
    }
}
