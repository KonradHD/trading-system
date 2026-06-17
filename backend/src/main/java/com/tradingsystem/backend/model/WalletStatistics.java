package com.tradingsystem.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "wallet_statistics")
public class WalletStatistics {

    @Id
    private Long walletId;

    private Long userId;
    private Long totalTrades;
    private BigDecimal totalCommissionsPaid;
    private BigDecimal volumeBalance;

    public Long getWalletId() { return walletId; }
    public Long getUserId() { return userId; }
    public Long getTotalTrades() { return totalTrades; }
    public BigDecimal getTotalCommissionsPaid() { return totalCommissionsPaid; }
    public BigDecimal getVolumeBalance() { return volumeBalance; }
}