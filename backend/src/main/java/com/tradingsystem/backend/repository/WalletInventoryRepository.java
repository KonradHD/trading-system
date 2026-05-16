package com.tradingsystem.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.WalletInventory;
import com.tradingsystem.backend.model.WalletInventoryId;


public interface WalletInventoryRepository extends JpaRepository<WalletInventory, WalletInventoryId>{
    
    List<WalletInventory> findAllByWalletId(Long walletId);
}
