package com.tradingsystem.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tradingsystem.backend.controller.WalletController;
import com.tradingsystem.backend.dto.*;
import com.tradingsystem.backend.exception.NotEnoughResourcesException;
import com.tradingsystem.backend.exception.UserNotFoundException;
import com.tradingsystem.backend.exception.WalletNotFoundException;
import com.tradingsystem.backend.model.User;
import com.tradingsystem.backend.model.Wallet;
import com.tradingsystem.backend.repository.UserRepository;
import com.tradingsystem.backend.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tradingsystem.backend.dto.ActiveWallet.createActiveWallet;
import static com.tradingsystem.backend.dto.NewWalletResponse.createWalletResponse;
import static com.tradingsystem.backend.exception.UserNotFoundException.userNotFoundException;
import static com.tradingsystem.backend.exception.WalletNotFoundException.createWalletNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final RedisPublisherService redisPublisher;

    @Transactional(readOnly = true)
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Transactional
    public NewWalletResponse createWallet(Long userId, NewWalletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::userNotFoundException);

        Wallet wallet = Wallet.builder()
                .user(user)
                .name(request.name())
                .availableFunds(request.availableFunds())
                .activeTrades(request.activeTrades())
                .build();

        walletRepository.save(wallet);

        return createWalletResponse(wallet);
    }

    @Transactional
    public void deleteWallet(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> createWalletNotFoundException(walletId));

        wallet.setIsActive(false);
    }

    public List<ActiveWallet> getActiveWallets() {
        List<Wallet> wallets = walletRepository.findAllByActiveTrades(true);
        ActiveContext context = new ActiveContext("all", BigDecimal.valueOf(1000000));

        return wallets.stream()
                .map(wallet -> createActiveWallet(wallet.getId(), context))
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean switchActivity(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> createWalletNotFoundException(walletId));

        ActiveContext context = new ActiveContext("all", BigDecimal.valueOf(1000000));
        Boolean newStatus = !wallet.getActiveTrades();
        wallet.setActiveTrades(newStatus);

        if (newStatus) {
            redisPublisher.notifyBotToStart(walletId, context);
        } else {
            redisPublisher.notifyBotToStop(walletId, context);
        }

        return newStatus;
    }

    @Transactional(readOnly = true)
    public List<WalletKeysDTO> getWalletsKeys(List<Long> walletIds) {
        List<Wallet> wallets = walletRepository.findAllWithUserByIdIn(walletIds);

        return wallets.stream()
                .map(wallet -> new WalletKeysDTO(
                        wallet.getId(),
                        wallet.getUser().getBinanceApiKey(),
                        wallet.getUser().getBinanceSecretKey()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Wallet updateWallet(Long walletId, TransactionDTO transaction) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> createWalletNotFoundException(walletId));

        BigDecimal quantity = BigDecimal.valueOf(transaction.quantity());
        BigDecimal transactionCost = quantity.multiply(transaction.priceQty());
        BigDecimal walletFunds = wallet.getAvailableFunds();

        if (transactionCost.compareTo(walletFunds) > 0) {
            throw new NotEnoughResourcesException(
                    "Transaction cost is: %s but wallet funds are: %s".formatted(transactionCost, walletFunds)
            );
        }

        wallet.setAvailableFunds(walletFunds.subtract(transactionCost));

        return wallet;
    }

    @Transactional(readOnly = true)
    public List<Wallet> getWallets(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::userNotFoundException);

        return walletRepository.findAllByUser(user);
    }

    public Wallet getWallet(Long walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(() -> createWalletNotFoundException(walletId));
    }
}