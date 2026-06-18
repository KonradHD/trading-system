package com.tradingsystem.backend.service;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tradingsystem.backend.dto.authentication.TokenJwt;
import com.tradingsystem.backend.model.User;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenJwtService {

    @Value("${app.security.jwt.secret}")
    private String secretKey;

    @Value("${app.security.jwt.expiration.miliseconds}")
    private Long expirationTime;

    public TokenJwt generateToken(User authenticatedUser){
        Date createdAt = new Date();
        Date expiresAt = new Date(System.currentTimeMillis() + expirationTime);

        String token = Jwts.builder()
                    .setSubject(authenticatedUser.getLogin())
                    .claim("role", authenticatedUser.getRole())
                    .claim("userId", authenticatedUser.getId())
                    .setIssuedAt(createdAt)
                    .setExpiration(expiresAt)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();

        return TokenJwt.createTokenJwt(token, expiresAt);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        Number userIdNumber = claims.get("userId", Number.class);
        return userIdNumber != null ? userIdNumber.longValue() : null;
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
