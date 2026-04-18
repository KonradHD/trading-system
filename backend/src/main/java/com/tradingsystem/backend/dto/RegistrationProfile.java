package com.tradingsystem.backend.dto;

import java.time.LocalDate;

import com.tradingsystem.backend.adnotation.ValidPesel;
import com.tradingsystem.backend.adnotation.ValidPhoneNumber;
import com.tradingsystem.backend.utils.types.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;


public record RegistrationProfile (
    @NotBlank @Email(message="Invalid email address") String email,
    @NotBlank String name,
    @NotBlank String surname,
    @NotNull Gender gender,
    @NotNull @Past(message="Date of birth must be in the past") LocalDate dateOfBirth,
    @NotBlank String city,
    @NotBlank String address,
    @NotBlank @ValidPhoneNumber String phone,
    @NotBlank @ValidPesel String pesel
){

}
