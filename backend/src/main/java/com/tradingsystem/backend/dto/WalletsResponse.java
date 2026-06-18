package com.tradingsystem.backend.dto;

import java.util.List;

import com.tradingsystem.backend.dto.InventoryDTO;

import com.tradingsystem.backend.model.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WalletsResponse (
    @Schema(description = "Unique identifier of the wallet") @NotNull Long id,
    @NotBlank String name,
    @NotNull Boolean activeTrades,
    @Schema(description = "List of inventories affected") List<InventoryDTO> inventories
){
    public static WalletsResponse createWalletsResponse(Wallet wallet, List<InventoryDTO> inventories){
        return new WalletsResponse(wallet.getId(), wallet.getName(), wallet.getActiveTrades(), inventories);
    }
}
