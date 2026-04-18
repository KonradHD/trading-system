package com.tradingsystem.trading_bot.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tradingsystem.trading_bot.utils.MarketActions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("side")
    private MarketActions action;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transactTime")
    private Long timestamp;

    @JsonProperty("executedQty")
    private Double quantity;

    @JsonProperty("cummulativeQuoteQty")
    private BigDecimal priceQty;

    @JsonProperty("type")
    private String type;

    @JsonProperty("fills")
    private List<CommissionDTO> commissions;
}
