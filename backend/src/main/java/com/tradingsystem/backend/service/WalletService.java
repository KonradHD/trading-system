package com.tradingsystem.backend.service;

import java.math.BigDecimal;

import com.tradingsystem.backend.exception.UserNotFoundException;
import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.tradingsystem.backend.dto.NewWalletRequest;
import com.tradingsystem.backend.dto.NewWalletResponse;
import com.tradingsystem.backend.model.Wallet;
import com.tradingsystem.backend.repository.WalletRepository;
import static com.tradingsystem.backend.dto.NewWalletResponse.createWalletResponse;
import static com.tradingsystem.backend.exception.UserNotFoundException.userNotFoundException;
import static com.tradingsystem.backend.exception.WalletNotFoundException.walletNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Transactional
    public NewWalletResponse createWallet(Long userId, NewWalletRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::userNotFoundException);

        Wallet wallet = Wallet.builder()
                .user(user)
                .name(request.name())
                .availableFunds(BigDecimal.ZERO)
                .build();
        walletRepository.save(wallet);

        return createWalletResponse(wallet);
    }


    public void deleteWallet(Long walletId){
        Wallet wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> walletNotFoundException(walletId));
        wallet.setIsActive(false);
    }
}
