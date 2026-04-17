package com.tradingsystem.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
