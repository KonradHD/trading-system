package com.tradingsystem.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.WalletStatistics;

public interface WalletStatisticsRepository extends JpaRepository<WalletStatistics, Long> {
    Optional<WalletStatistics> findByWalletId(Long walletId);
}