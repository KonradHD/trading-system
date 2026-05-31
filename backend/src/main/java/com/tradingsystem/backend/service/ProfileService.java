package com.tradingsystem.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tradingsystem.backend.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.tradingsystem.backend.dto.ActiveContext;
import com.tradingsystem.backend.dto.ActiveUser;
import static com.tradingsystem.backend.dto.ActiveUser.createActiveUser;
import static com.tradingsystem.backend.exception.UserNotFoundException.userNotFoundException;
import com.tradingsystem.backend.model.Profile;
import com.tradingsystem.backend.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

}
