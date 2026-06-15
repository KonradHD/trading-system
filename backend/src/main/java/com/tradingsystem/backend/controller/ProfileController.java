package com.tradingsystem.backend.controller;

import com.tradingsystem.backend.dto.ProfileDTO;
import com.tradingsystem.backend.model.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.tradingsystem.backend.service.ProfileService;
import com.tradingsystem.backend.utils.ResponseMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.tradingsystem.backend.dto.ProfileDTO.createProfileDTO;

@RestController
@RequestMapping("/api/profile")
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal Long userId) {
        log.info("Received request to retrieve profile for user: {}", userId);
        Profile profile = profileService.forUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(createProfileDTO(profile));
    }
}
