package com.tradingsystem.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.InternalKeysRequest;
import com.tradingsystem.backend.dto.UserKeysDTO;
import com.tradingsystem.backend.dto.UserKeysResponse;
import static com.tradingsystem.backend.dto.UserKeysResponse.createUserKeysResponse;
import com.tradingsystem.backend.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/internal/users")
@Slf4j
@RequiredArgsConstructor
public class InternalUserController {
    
    private final UserService userService;

    @Value("${app.security.internal-api-secret}")
    private String internalSecret; 

    @PostMapping(value = "/keys", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserKeysResponse> getUsersKeys(
        @RequestBody @Valid InternalKeysRequest request,
        @RequestHeader( value = "X-Internal-Secret", required = false) String requestSecret
    ){
        log.info("Received internal request for users binance keys.");
        if (!internalSecret.equals(requestSecret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<UserKeysDTO> keys = userService.getUsersKeys(request.userIds());
        return ResponseEntity.status(HttpStatus.OK).body(createUserKeysResponse(keys));
    }
}
