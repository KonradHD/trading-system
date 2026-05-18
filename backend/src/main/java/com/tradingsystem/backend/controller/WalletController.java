package com.tradingsystem.backend.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.InventoryDTO;
import com.tradingsystem.backend.dto.NewWalletRequest;
import com.tradingsystem.backend.dto.NewWalletResponse;
import static com.tradingsystem.backend.dto.WalletsResponse.createWalletsResponse;
import com.tradingsystem.backend.service.WalletInventoryService;
import com.tradingsystem.backend.service.WalletService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/wallet")
@Slf4j
@RequiredArgsConstructor
public class WalletController {

    private final WalletInventoryService walletInventoryService;
    private final WalletService walletService;


    @GetMapping(value = "/{wallet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> walletData(
        @Parameter(description = "ID of the wallet to retrieve")
        @PathVariable("wallet_id") Long walletId){
        log.info("Received request for wallet inventories data with id: {}", walletId);

        List<InventoryDTO> walletInventories = walletInventoryService.getWalletInventoriesDTO(walletId);

        return ResponseEntity.status(HttpStatus.OK).body(createWalletsResponse(walletId, walletInventories));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewWalletResponse> createWallet(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @RequestBody(required = true) @Valid NewWalletRequest request
    ){
        log.info("Received request to create new wallet");
        NewWalletResponse newWallet = walletService.createWallet(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWallet);
    }

    @DeleteMapping(value = "/{wallet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteWallet(@PathVariable("wallet_id") Long walletId){
        log.info("Received request to delete wallet with id: {}", walletId);

        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }
}
