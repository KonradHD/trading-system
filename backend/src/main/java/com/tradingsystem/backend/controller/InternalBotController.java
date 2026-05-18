package com.tradingsystem.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.ActiveUser;
import com.tradingsystem.backend.dto.ActiveUsersResponse;
import static com.tradingsystem.backend.dto.ActiveUsersResponse.createActiveUsersResponse;
import com.tradingsystem.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/internal/bot")
@Slf4j
@RequiredArgsConstructor
public class InternalBotController {

    private final ProfileService profileService;   

    @Value("${app.security.internal-api-secret}")
    private String internalSecret; 

    @GetMapping(value = "/active", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActiveUsersResponse> getActiveUsers(@RequestHeader(value = "X-Internal-Secret", required = false) String requestSecret){
        log.info("Received internal request for active users.");
        if (!internalSecret.equals(requestSecret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ActiveUser> activeUsers = profileService.getActiveUsers();
        return ResponseEntity.status(HttpStatus.OK).body(createActiveUsersResponse(activeUsers));
    }
}
