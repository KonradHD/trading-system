package com.tradingsystem.backend.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InventoryDTO (
    @Schema(description = "Unique stock symbol") @NotBlank @Size(max = 20, message = "Stock symbol cannot overcome 20 letters.") String stockSymbol,
    @Schema(description = "Quantity of the concerned stock") @NotNull @Min(value = 0, message = "Stock quantity cannot be negative.") BigDecimal quantity
){

    public InventoryDTO(String stockSymbol, BigDecimal quantity){
        this.quantity = quantity;
        this.stockSymbol = stockSymbol.toUpperCase(); 
    }

    public static InventoryDTO createInventoryDTO(String stockSymbol, BigDecimal quantity){
        return new InventoryDTO(stockSymbol, quantity);
    }
}

