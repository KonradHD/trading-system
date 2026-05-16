package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record BinanceSecret(
    @NotBlank String binanceApiKey,
    @NotBlank String binanceSecretKey
){

}
