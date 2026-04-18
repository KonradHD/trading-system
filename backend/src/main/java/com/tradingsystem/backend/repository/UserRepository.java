package com.tradingsystem.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}
