package com.tradingsystem.backend.service;

import org.springframework.stereotype.Service;

import com.tradingsystem.backend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
