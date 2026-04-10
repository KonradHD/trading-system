package com.tradingsystem.trading_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.trading_bot.model.OpenInterestEntity;

public interface OpenInterestRepository extends JpaRepository<OpenInterestEntity, Long>{

}
