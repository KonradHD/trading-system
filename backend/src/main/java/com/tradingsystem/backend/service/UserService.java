package com.tradingsystem.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tradingsystem.backend.dto.RegistrationProfile;
import com.tradingsystem.backend.dto.user.UserRegistration;
import com.tradingsystem.backend.dto.user.UserResponse;
import com.tradingsystem.backend.exception.UserAlreadyExistsException;
import static com.tradingsystem.backend.exception.UserAlreadyExistsException.userAlreadyExistsException;
import com.tradingsystem.backend.model.Profile;
import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.repository.ProfileRepository;
import com.tradingsystem.backend.repository.UserRepository;
import com.tradingsystem.backend.utils.types.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public UserResponse saveUser(UserRegistration userRegistration){
        if(userRepository.existsByLogin(userRegistration.login()) || profileRepository.existsByEmail(userRegistration.profile().email())){
            throw userAlreadyExistsException();
        }
        var passwordEncoded = passwordEncoder.encode(userRegistration.password());
        User user = mapToUser(userRegistration, passwordEncoded);
        userRepository.save(user);
        return UserResponse.createUserResponse(user);
    }


    private User mapToUser(UserRegistration userRegistration, String password){
        User user = User.builder()
                    .login(userRegistration.login())
                    .password(password)
                    .role(Role.USER)
                    .build();

        RegistrationProfile registrationProfile = userRegistration.profile();
        Profile profile = Profile.builder()
                            .email(registrationProfile.email())
                            .name(registrationProfile.name())
                            .surname(registrationProfile.surname())
                            .gender(registrationProfile.gender())
                            .dateOfBirth(registrationProfile.dateOfBirth())
                            .city(registrationProfile.city())
                            .address(registrationProfile.address())
                            .phone(registrationProfile.phone())
                            .pesel(registrationProfile.pesel())
                            .build();
        
        profile.setUser(user);
        user.setProfile(profile);
        return user;
    }
}