package com.tradingsystem.backend.controller;

import java.util.List;

import com.tradingsystem.backend.dto.WalletKeysDTO;
import com.tradingsystem.backend.dto.WalletKeysResponse;
import com.tradingsystem.backend.model.Wallet;
import com.tradingsystem.backend.service.WalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tradingsystem.backend.dto.InternalKeysRequest;
import static com.tradingsystem.backend.dto.WalletKeysResponse.createWalletKeysResponse;
import static com.tradingsystem.backend.dto.WalletSynchroResponse.createWalletSynchroResponse;

import com.tradingsystem.trading_bot.dto.TransactionDTO;
import com.tradingsystem.backend.dto.WalletSynchroResponse;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/internal/wallets")
@Slf4j
@RequiredArgsConstructor
public class InternalWalletController {

    private final WalletService walletService;

    @Value("${app.security.internal-api-secret}")
    private String internalSecret; 

    @PostMapping(value = "/keys", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletKeysResponse> getUsersKeys(
        @RequestBody @Valid InternalKeysRequest request,
        @RequestHeader( value = "X-Internal-Secret", required = false) String requestSecret
    ){
        log.info("Received internal request for users binance keys.");
        if (!internalSecret.equals(requestSecret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<WalletKeysDTO> keys = walletService.getWalletsKeys(request.walletIds());
        return ResponseEntity.status(HttpStatus.OK).body(createWalletKeysResponse(keys));
    }

    @PutMapping(value = "/wallet_id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletSynchroResponse> synchronizeWallet(
            @PathVariable("wallet_id") Long walletId,
            @RequestBody @Valid TransactionDTO transaction,
            @RequestHeader( value = "X-Internal-Secret", required = false) String requestSecret
    ){
        log.info("Received wallet ({}) synchronization request after transaction: {}.", walletId, transaction.getOrderId());
        if (!internalSecret.equals(requestSecret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Wallet wallet = walletService.updateWallet(walletId, transaction);
        return ResponseEntity.status(HttpStatus.OK).body(createWalletSynchroResponse(wallet, "Success", "Wallet was updated"));
    }

}
