package com.java017.tripblog.security;

import com.java017.tripblog.filter.AfterLoginFilter;
import com.java017.tripblog.filter.BeforeLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.Collections;


/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:28
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;
    private final UserDetailsService myUserDetailsService;
    private final AuthenticationSuccessHandler mySuccessHandler;
    private final AuthenticationFailureHandler myFailureHandler;

    @Autowired
    public WebSecurityConfig(DataSource dataSource, UserDetailsService myUserDetailsService, AuthenticationSuccessHandler mySuccessHandler, AuthenticationFailureHandler myFailureHandler) {
        this.dataSource = dataSource;
        this.myUserDetailsService = myUserDetailsService;
        this.mySuccessHandler = mySuccessHandler;
        this.myFailureHandler = myFailureHandler;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //首次創建Token表
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //防止8080路徑變更
        PortMapperImpl portMapper = new PortMapperImpl();
        portMapper.setPortMappings(Collections.singletonMap("8080", "8080"));
        PortResolverImpl portResolver = new PortResolverImpl();
        portResolver.setPortMapper(portMapper);
        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint(
                "/user/loginPage");
        entryPoint.setPortMapper(portMapper);
        entryPoint.setPortResolver(portResolver);
        http.exceptionHandling().authenticationEntryPoint(entryPoint);

        http
                .authorizeRequests()
                .antMatchers("/*","/article/**", "/shop/**", "/MapSearch/*", "/user/signup", "/user/accountCheck/**", "/user/signup-success", "/captcha/**", "/**/*.js", "/**/*.css", "/**/*.svg", "/**/*.png", "/**/*.jpg")
                .permitAll()
                .antMatchers("/user/**", "/shop/orderList").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/user/loginPage")
                .loginProcessingUrl("/user/login")
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler)
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .permitAll()

                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60 * 60 * 24 * 7)
                .userDetailsService(myUserDetailsService)
                .key("TripBlog017")

                .and()
                .csrf()
                .ignoringAntMatchers("/");

        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
