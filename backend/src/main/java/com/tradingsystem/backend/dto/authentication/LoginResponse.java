package com.tradingsystem.backend.dto.authentication;

import com.tradingsystem.backend.dto.user.UserResponse;

public record LoginResponse (
    UserResponse user,
    TokenJwt accessToken
){
    public static LoginResponse createLoginResponse(UserResponse user, TokenJwt token){
        return new LoginResponse(user, token);
    }
}
