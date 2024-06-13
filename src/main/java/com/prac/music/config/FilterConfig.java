package com.prac.music.config;

import com.prac.music.domain.user.service.JwtService;
import com.prac.music.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final JwtService jwtService;

    @Autowired
    public FilterConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtService));
        registrationBean.addUrlPatterns("/api/users/*", "/api/boards/*");
        return registrationBean;
    }
}