package com.tradingsystem.backend.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.tradingsystem.backend.model.Token;
import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.repository.TokenRepository;
import com.tradingsystem.backend.utils.TokenEncrypter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final TokenRepository tokenRepository;
    private final TokenEncrypter encrypter;

    @Value("${refreshtoken.expiration.days}")
    private Integer expiration;

    public String generateRefreshToken(User authenticatedUser, String deviceInfo){
        String plainRefreshToken = UUID.randomUUID().toString() + UUID.randomUUID().toString();

        String encodedRefreshToken = encrypter.encode(plainRefreshToken);

        Token tokenEntity = Token.builder()
                .user(authenticatedUser)
                .refreshToken(encodedRefreshToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(expiration))
                .deviceInfo(deviceInfo)
                .build();
                
        tokenRepository.save(tokenEntity);
        return plainRefreshToken;
    }

    public User validateAndGetUser(String plainRefreshToken) {   
        String hashedToken = encrypter.encode(plainRefreshToken);
        Token dbToken = tokenRepository.findByRefreshToken(hashedToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (dbToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(dbToken);
            throw new BadCredentialsException("Refresh token expired. Sign in again");
        }

        return dbToken.getUser();
    }
}
