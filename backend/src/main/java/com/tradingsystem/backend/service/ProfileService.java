package com.tradingsystem.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tradingsystem.backend.exception.UserNotFoundException;
import com.tradingsystem.backend.model.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;


import com.tradingsystem.backend.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

import static com.tradingsystem.backend.exception.UserNotFoundException.userNotFoundException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile forUser(Long userId) {
        return profileRepository.findById(userId).orElseThrow(
                UserNotFoundException::userNotFoundException
        );
    }
}
