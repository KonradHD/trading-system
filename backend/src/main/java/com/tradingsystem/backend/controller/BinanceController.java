package com.tradingsystem.backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.BinanceSecret;
import com.tradingsystem.backend.service.UserService;
import com.tradingsystem.backend.utils.ResponseMessage;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/binance")
@Slf4j
@RequiredArgsConstructor
public class BinanceController {

    private final UserService userService;

    @PostMapping(value = "/keys", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> setBinanceKeys(
        @AuthenticationPrincipal Long userId, 
        @RequestBody @Valid BinanceSecret request){
        log.info("Received binance secrets for user: {}", userId);
        
        userService.saveBinanceKeys(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(   
            new ResponseMessage("Success", "Binance keys were successfully saved")
        );

    }
}
