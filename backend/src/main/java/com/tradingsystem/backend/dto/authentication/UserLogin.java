package com.tradingsystem.backend.dto.authentication;

import com.tradingsystem.backend.adnotation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLogin (
    @NotBlank(message = "Username is required") String login,
    @NotBlank(message = "Password is required") @ValidPassword String password,
    @Size(max=50) String deviceInfo
){

}
