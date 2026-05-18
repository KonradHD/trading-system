package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public record NewWalletRequest(
    @JsonProperty("name") @NotBlank(message = "Wallet name cannot be an empty string.") String name
) {
    @JsonCreator
    public NewWalletRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
