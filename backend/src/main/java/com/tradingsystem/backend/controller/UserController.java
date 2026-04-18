package com.tradingsystem.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.user.UserRegistration;
import com.tradingsystem.backend.dto.user.UserResponse;
import com.tradingsystem.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    
    // tylko format "application/json"
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid UserRegistration request){
        log.info("Received user registration request: {}", request.login());
        UserResponse userResponse = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
