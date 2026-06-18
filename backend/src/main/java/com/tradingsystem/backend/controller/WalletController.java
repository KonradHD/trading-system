package com.tradingsystem.backend.controller;

import java.util.List;

import com.tradingsystem.backend.model.Wallet;
import com.tradingsystem.backend.repository.WalletRepository;
import com.tradingsystem.backend.utils.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.tradingsystem.backend.dto.InventoryDTO;
import com.tradingsystem.backend.dto.NewWalletRequest;
import com.tradingsystem.backend.dto.NewWalletResponse;

import static com.tradingsystem.backend.dto.WalletListDTO.createWalletsListDTO;
import static com.tradingsystem.backend.dto.WalletsResponse.createWalletsResponse;
import com.tradingsystem.backend.service.WalletInventoryService;
import com.tradingsystem.backend.service.WalletService;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllWallets() {
        log.info("Received request for all wallets");
        return ResponseEntity.status(HttpStatus.OK).body(walletService.getAllWallets());
    }

    @GetMapping(value = "/{wallet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> walletData(
            @AuthenticationPrincipal Long userId,
            @Parameter(description = "ID of the wallet to retrieve")
            @PathVariable("wallet_id") Long walletId) {
        log.info("Received request for wallet inventories data with id: {}", walletId);
        Wallet wallet = walletService.getWallet(walletId);
        List<InventoryDTO> walletInventories = walletInventoryService.getWalletInventoriesDTO(walletId);

        return ResponseEntity.status(HttpStatus.OK).body(createWalletsResponse(wallet, walletInventories));
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> walletsList(@AuthenticationPrincipal Long userId) {
        log.info("Received request for wallets data for user: {}", userId);

        List<Wallet> wallets = walletService.getWallets(userId);
        return ResponseEntity.status(HttpStatus.OK).body(createWalletsListDTO(wallets));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewWalletResponse> createWallet(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody(required = true) @Valid NewWalletRequest request
    ) {
        log.info("Received request to create new wallet");
        NewWalletResponse newWallet = walletService.createWallet(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWallet);
    }

    @DeleteMapping(value = "/{wallet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteWallet(@PathVariable(value = "wallet_id") Long walletId) {
        log.info("Received request to delete wallet with id: {}", walletId);

        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/active/switch/{wallet_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> switchTradesActivity(@PathVariable(value = "wallet_id") Long walletId) {
        log.info("Received request to switch trades activity for wallet: {}", walletId);
        Boolean activityStatus = walletService.switchActivity(walletId);

        String message = activityStatus
                ? "Bot trades for wallet: %s is now active.".formatted(walletId)
                : "Bot trades for wallet: %s is now disabled.".formatted(walletId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage("Success", message)
        );
    }
}