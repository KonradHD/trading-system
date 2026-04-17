package com.tradingsystem.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    
}
