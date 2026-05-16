package com.tradingsystem.backend.dto;

import java.util.List;

import com.tradingsystem.backend.dto.InventoryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record WalletsResponse (
    @Schema(description = "Unique identifier of the wallet") @NotNull Long id,
    @Schema(description = "List of inventories affected") List<InventoryDTO> inventories
){
    public static WalletsResponse createWalletsResponse(Long walletId, List<InventoryDTO> inventories){
        return new WalletsResponse(walletId, inventories);
    }
}
