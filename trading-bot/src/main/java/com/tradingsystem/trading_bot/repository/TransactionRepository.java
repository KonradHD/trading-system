package com.tradingsystem.trading_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.trading_bot.model.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    
}
