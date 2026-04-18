package com.tradingsystem.backend.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tradingsystem.backend.adnotation.ValidPassword;
import com.tradingsystem.backend.dto.RegistrationProfile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UserRegistration (
    
    @NotBlank String login,
    @NotBlank @ValidPassword String password,
    @JsonProperty("profile") @Valid RegistrationProfile profile
){

}
