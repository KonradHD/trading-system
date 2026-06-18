package com.tradingsystem.backend.repository;

import com.tradingsystem.backend.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.Wallet;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Wallet> findAllByActiveTrades(Boolean activeTrades);

    @EntityGraph(attributePaths = {"user"})
    List<Wallet> findAllWithUserByIdIn(List<Long> walletIds);

    List<Wallet> findAllByUser(User user);
}
