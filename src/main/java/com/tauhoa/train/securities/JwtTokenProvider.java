package com.tauhoa.train.securities;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final RefreshTokenService refreshTokenService;
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.refresh-expiration}")
    private int refreshTokenExpiration;

    public JwtTokenProvider(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    public String createAccessToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration * 1000L);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }


    public String createRefreshToken(String userId) {
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(userId);
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(new Date(now.getTime() + refreshTokenExpiration * 1000L));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false; //토큰 만료
        } catch (Exception e) {
            return false; //토큰 손상
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true; //토큰 만료
        } catch (Exception e) {
            return false; //토큰 손상
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
