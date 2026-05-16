package com.tradingsystem.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
}
