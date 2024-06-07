package com.prac.music.domain.user.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String createToken(String userId) {
        Date now = new Date();
        Long minute = 30L * 60 * 1000;
        Date validity = new Date(now.getTime() + minute); // 30분 유효

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userId) { // 리프레시 토큰 생성
        Date now = new Date();
        Long twoWeek = 14L * 24 * 60 * 60 * 1000;
        Date validity = new Date(now.getTime() + twoWeek);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userId)
                        .setIssuedAt(now)
                        .setExpiration(validity)
                        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                        .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        } catch (ExpiredJwtException e){
            throw new AccessDeniedException("재로그인 해주세요");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String substringToken (String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        return substringToken(bearerToken);
    }

    public String getRefreshJwtFromHeader(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        return substringToken(refreshToken);
    }

    public Boolean isTokenExpired(String token){
        Claims claims = getUserInfoFromToken(token);
        Date date = claims.getExpiration();
        return date.before(new Date());
    }

    public Boolean isRefreshTokenExpired(String refreshToken){
        String reToken = refreshToken.substring(7);
        Claims claims = getUserInfoFromToken(reToken);
        Date date = claims.getExpiration();
        return date.before(new Date());
    }
}