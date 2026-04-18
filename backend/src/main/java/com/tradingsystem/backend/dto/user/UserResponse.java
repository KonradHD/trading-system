package com.tradingsystem.backend.dto.user;

import java.time.LocalDate;

import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.utils.types.Gender;
import com.tradingsystem.backend.utils.types.Role;

public record UserResponse (
    Long id,
    String login,
    Role role,
    String email,
    String name,
    String surname, 
    Gender gender,
    LocalDate dateOfBirth,
    String city,
    String address,
    String phone,
    String pesel

){
    
    public static UserResponse createUserResponse(User user){
        return new UserResponse(
            user.getId(),
            user.getLogin(),
            user.getRole(),
            user.getProfile().getEmail(),
            user.getProfile().getName(),
            user.getProfile().getSurname(),
            user.getProfile().getGender(),
            user.getProfile().getDateOfBirth(),
            user.getProfile().getCity(),
            user.getProfile().getAddress(),
            user.getProfile().getPhone(),
            user.getProfile().getPesel()
        );
    }
}
