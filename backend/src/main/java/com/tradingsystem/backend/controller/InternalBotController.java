package com.tradingsystem.backend.controller;

import java.util.List;

import com.tradingsystem.backend.service.WalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.ActiveWallet;
import com.tradingsystem.backend.dto.ActiveWalletsResponse;
import static com.tradingsystem.backend.dto.ActiveWalletsResponse.createActiveWalletsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/internal/bot")
@Slf4j
@RequiredArgsConstructor
public class InternalBotController {

    private final WalletService walletService;

    @Value("${app.security.internal-api-secret}")
    private String internalSecret; 

    @GetMapping(value = "/active", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActiveWalletsResponse> getActiveWallets(@RequestHeader(value = "X-Internal-Secret", required = false) String requestSecret){
        log.info("Received internal request for active wallets.");
        if (!internalSecret.equals(requestSecret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ActiveWallet> activeUsers = walletService.getActiveWallets();
        return ResponseEntity.status(HttpStatus.OK).body(createActiveWalletsResponse(activeUsers));
    }
}
