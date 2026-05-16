package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record NewWalletRequest(
    @NotBlank String name
) {
    
}
