package com.prac.music.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import com.prac.music.domain.user.security.UserDetailsImpl;
import com.prac.music.domain.user.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   AuthenticationManager authenticationManager,
                                   UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String userId = user.getUserId();

        String token = jwtService.createToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);

        response.addHeader(JwtService.AUTHORIZATION_HEADER, token);
        response.addHeader(JwtService.REFRESH_TOKEN_HEADER, refreshToken);

        user.updateRefresh(refreshToken);
        userRepository.save(user);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus(401);
    }
}