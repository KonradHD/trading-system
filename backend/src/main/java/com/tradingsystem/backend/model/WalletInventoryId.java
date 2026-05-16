package com.tradingsystem.backend.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WalletInventoryId implements Serializable {
    @Column(name = "wallet_id")
    private Long walletId;

    @Column(name = "stock_symbol")
    private String stockSymbol;
}