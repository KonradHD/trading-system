package com.tradingsystem.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingsystem.backend.dto.authentication.LoginResponse;
import static com.tradingsystem.backend.dto.authentication.LoginResponse.createLoginResponse;
import com.tradingsystem.backend.dto.authentication.TokenJwt;
import com.tradingsystem.backend.dto.authentication.UserLogin;
import com.tradingsystem.backend.dto.user.UserResponse;
import static com.tradingsystem.backend.dto.user.UserResponse.createUserResponse;
import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.service.AuthenticationService;
import com.tradingsystem.backend.service.RefreshTokenService;
import com.tradingsystem.backend.service.TokenJwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final TokenJwtService tokenJwtService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> signIn(@RequestBody @Valid UserLogin userLogin){
        log.info("Received signing in request from {}", userLogin.login());
        User authenticatedUser = authenticationService.authenticate(userLogin);
        TokenJwt accessToken = tokenJwtService.generateToken(authenticatedUser);

        String refreshToken = refreshTokenService.generateRefreshToken(authenticatedUser, userLogin.deviceInfo());

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                            .httpOnly(true)
                            .secure(false)
                            .path("/auth/refresh")
                            .maxAge(7 * 24 * 60 * 60)
                            .sameSite("Strict")
                            .build();

        UserResponse user = createUserResponse(authenticatedUser);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(createLoginResponse(user, accessToken));
    }

    @PostMapping(value="/refresh", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenJwt> checkSession(@CookieValue(name = "refresh_token", required = false) String plainRefreshToken){
        log.info("Received refreshing request");

        if (plainRefreshToken == null) {
        throw new BadCredentialsException("No valid refresh token. Sign in again.");
        }
        User user = refreshTokenService.validateAndGetUser(plainRefreshToken);
        TokenJwt newAccessToken = tokenJwtService.generateToken(user);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(newAccessToken);
    }
}
