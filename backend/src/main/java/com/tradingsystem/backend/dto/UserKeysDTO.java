package com.tradingsystem.backend.dto;

import com.tradingsystem.backend.model.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserKeysDTO(
    @NotNull Long userId,
    @NotBlank String apiKey,
    @NotBlank String secretKey
) {
    public static UserKeysDTO createUserKeysDTO(User user){
        return new UserKeysDTO(user.getId(),user.getBinanceApiKey(),user.getBinanceSecretKey());
    }
}
