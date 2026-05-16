package com.tradingsystem.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tradingsystem.backend.exception.WalletNotFoundException.walletNotFoundException;
import static com.tradingsystem.backend.dto.InventoryDTO.createInventoryDTO;
import com.tradingsystem.backend.dto.InventoryDTO;
import com.tradingsystem.backend.model.WalletInventory;
import com.tradingsystem.backend.repository.WalletInventoryRepository;
import com.tradingsystem.backend.repository.WalletRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletInventoryService {

    private final WalletInventoryRepository inventoryRepository;
    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public List<InventoryDTO> getWalletInventoriesDTO(Long walletId){
        walletRepository.findById(walletId)
            .orElseThrow(() -> walletNotFoundException(walletId));

        List<WalletInventory> inventories = inventoryRepository.findAllByWalletId(walletId);

        return inventories.stream()
                    .map(inv -> createInventoryDTO(inv.getId().getStockSymbol(), inv.getQuantity()))
                    .collect(Collectors.toList());
    }
}
