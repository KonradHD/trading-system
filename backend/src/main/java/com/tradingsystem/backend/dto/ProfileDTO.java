package com.tradingsystem.backend.dto;

import com.tradingsystem.backend.adnotation.ValidPesel;
import com.tradingsystem.backend.adnotation.ValidPhoneNumber;
import com.tradingsystem.backend.model.Profile;
import com.tradingsystem.backend.utils.types.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record ProfileDTO(
    @Email String email,
    @NotBlank String name,
    @NotBlank String surname,
    @Valid Gender gender,
    @Past LocalDate dateOfBirth,
    @NotBlank String address,
    @ValidPhoneNumber String phone
) {

    public static ProfileDTO createProfileDTO(Profile profile) {
        return new ProfileDTO(
                profile.getEmail(),
                profile.getName(),
                profile.getSurname(),
                profile.getGender(),
                profile.getDateOfBirth(),
                profile.getAddress(),
                profile.getPhone()
        );
    }
}
