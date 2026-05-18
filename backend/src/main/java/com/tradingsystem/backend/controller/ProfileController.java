package com.tradingsystem.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.tradingsystem.backend.service.ProfileService;
import com.tradingsystem.backend.utils.ResponseMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/profile")
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping(value="/active/switch/{user_id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> switchTradesActivity(@PathVariable(value="user_id") Long userId){
        log.info("Received request to switch trades activity for user: {}", userId);
        Boolean activityStatus = profileService.switchActivity(userId);
        String message = activityStatus ? "Bot trades for user: %s is now active.".formatted(userId) : "Bot trades for user: %s is now disabled.".formatted(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseMessage("Success", message)
        );
    }

}
