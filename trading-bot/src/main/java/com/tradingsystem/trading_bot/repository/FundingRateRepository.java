package com.tradingsystem.trading_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.trading_bot.model.FundingRateEntity;

public interface FundingRateRepository extends JpaRepository<FundingRateEntity, Integer> {
    
}
