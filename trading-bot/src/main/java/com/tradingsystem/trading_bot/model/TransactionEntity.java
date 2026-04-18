package com.tradingsystem.trading_bot.model;

import java.math.BigDecimal;
import java.util.List;

import com.tradingsystem.trading_bot.utils.MarketActions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@Table(name = "transactions")
@Builder
@NoArgsConstructor
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private MarketActions action;
    private String status;
    private Long timestamp;
    private Double quantity;
    private BigDecimal priceQty;
    private String type;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommissionEntity> commissions;
}
