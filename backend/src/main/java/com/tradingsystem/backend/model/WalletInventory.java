package com.tradingsystem.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet_inventory")
@Builder
@Getter @Setter
public class WalletInventory {
    @EmbeddedId
    @Builder.Default
    private WalletInventoryId id = new WalletInventoryId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("walletId")
    @JoinColumn(name = "wallet_id", nullable=false)
    private Wallet wallet;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;
}
