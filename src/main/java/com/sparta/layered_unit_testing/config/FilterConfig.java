package com.sparta.layered_unit_testing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sparta.layered_unit_testing.domain.user.service.JwtService;
import com.sparta.layered_unit_testing.jwt.JwtFilter;

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