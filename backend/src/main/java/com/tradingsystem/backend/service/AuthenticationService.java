package com.tradingsystem.backend.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tradingsystem.backend.dto.authentication.UserLogin;
import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User authenticate(UserLogin userLogin){
        User user = userRepository.findByLogin(userLogin.login())
                    .orElseThrow(() -> new BadCredentialsException("Incorrect login or password"));

        if(!passwordEncoder.matches(userLogin.password(), user.getPassword())){
            throw new BadCredentialsException("Incorrect login or password");
        }

        return user;
    }
}
