package com.prac.music.config;

import com.prac.music.domain.user.service.JwtService;
import com.prac.music.jwt.JwtFilter;
import com.prac.music.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtFilter(jwtService, userDetailsService));
		registrationBean.addUrlPatterns("/api/*"); // 필터를 적용할 URL 패턴 설정
		return registrationBean;
	}
}
