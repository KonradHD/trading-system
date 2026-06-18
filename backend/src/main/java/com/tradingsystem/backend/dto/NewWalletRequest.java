package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewWalletRequest(
    @JsonProperty("name") @NotBlank(message = "Wallet name cannot be an empty string.") String name,
    @JsonProperty("availableFunds") @NotNull BigDecimal availableFunds,
    @JsonProperty("activeTrades") @NotNull Boolean activeTrades
) {
    @JsonCreator
    public NewWalletRequest(
            @JsonProperty("name") String name,
            @JsonProperty("availableFunds") BigDecimal availableFunds,
            @JsonProperty("activeTrades") Boolean activeTrades) {
        this.name = name;
        this.availableFunds = availableFunds;
        this.activeTrades = activeTrades;
    }
}
