package com.tradingsystem.backend.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tradingsystem.backend.dto.authentication.TokenJwt;
import com.tradingsystem.backend.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenJwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.miliseconds}")
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
}
