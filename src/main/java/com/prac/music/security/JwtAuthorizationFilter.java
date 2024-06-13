package com.prac.music.security;

import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import com.prac.music.domain.user.security.UserDetailsServiceImpl;
import com.prac.music.domain.user.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    public JwtAuthorizationFilter(JwtService jwtService,
                                  UserDetailsServiceImpl userDetailsService,
                                  UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtService.getJwtFromHeader(request);

        log.info(tokenValue);
        if (StringUtils.hasText(tokenValue) && !request.getRequestURI().startsWith("/api/login")) {

            if (!jwtService.validateToken(tokenValue)) {
                log.error("Token Error");
                return;
            }

            Claims claims = jwtService.getUserInfoFromToken(tokenValue);
            log.info("Subject: " + claims.getSubject());
            String userId = claims.getSubject();

            // 사용자 조회
            User user = userRepository.findByUserId(userId).orElseThrow(
                    () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
            );

            if (!user.isExist()) {
                throw new IllegalStateException("탈퇴된 사용자입니다.");
            }

            String userRefreshToken = jwtService.substringToken(user.getRefreshToken());


            // 토큰이 만료된 경우
            if (jwtService.isTokenExpired(tokenValue)) {
                // 리프레시 토큰이 유효한 경우, 새로운 토큰 생성
                if (!jwtService.isRefreshTokenExpired(userRefreshToken)) {
                    jwtService.createToken(user.getUserId());
                    System.out.println("jwtService.createToken(user.getUserId()) = " + jwtService.createToken(user.getUserId()));
                    jwtService.createRefreshToken(user.getUserId());
                    log.info("새로운 토큰이 생성되었습니다.");
                    setAuthentication(claims.getSubject());
                    filterChain.doFilter(request, response);
                } else {
                    // 리프레시 토큰도 만료된 경우
                    throw new IllegalArgumentException("다시 재로그인해주세요");
                }
            } else {
                // 토큰이 유효한 경우
                setAuthentication(claims.getSubject());
            }
        }
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}