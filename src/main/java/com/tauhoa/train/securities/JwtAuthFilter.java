package com.tauhoa.train.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

@Component
public class JwtAuthFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.secretKey}")
    private String secretKeyString;

    public static final String HEADER_KEY = "Authorization";
    public static final String PREFIX = "Bearer ";

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String bearerToken = ((HttpServletRequest) servletRequest).getHeader(HEADER_KEY);

        if (bearerToken != null && bearerToken.startsWith(PREFIX)) {
            String token = bearerToken.substring(7);
            try {
                Claims claims = parseJwtClaims(token);
                setAuthentication(claims);
            } catch (ExpiredJwtException e) {
                System.out.println("🚨 Access Token이 만료되었어요! Refresh Token 확인 중..");

                String userId = e.getClaims().getSubject();
                String storedRefreshToken = refreshTokenService.getRefreshToken(userId);

                if (storedRefreshToken == null) {
                    System.out.println("🚨 Refresh Token이 없어요! 새로운 Refresh Token을 발급하고 Redis에 저장해요!");
                    String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);
                    refreshTokenService.saveRefreshToken(userId, newRefreshToken);
                    storedRefreshToken = newRefreshToken;
                }

                String newAccessToken = jwtTokenProvider.createAccessToken(userId);
                ((HttpServletResponse) servletResponse).setHeader("newAccessToken", newAccessToken);
                System.out.println("✅ 새로운 Access Token 발급이 완료됐어요!");
                Claims newClaims = jwtTokenProvider.getClaims(newAccessToken);
                setAuthentication(newClaims);

            } catch (Exception e) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                System.out.println("❌ Access Token이 유효하지 않아, Refresh Token도 발급받을 수 없어요!");
                httpServletResponse.setContentType("application/json");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Claims parseJwtClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        JwtParser parser = Jwts.parser().setSigningKey(secretKey).build();
        return parser.parseClaimsJws(token).getBody();
    }

    private void setAuthentication(Claims claims) {
        String userId = claims.getSubject();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
