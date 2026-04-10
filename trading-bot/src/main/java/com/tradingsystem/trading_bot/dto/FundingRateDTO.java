package com.tradingsystem.trading_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingRateDTO {
    
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("fundingTime")
    private Long timestamp;

    @JsonProperty("fundingRate")
    private Double fundingRate;

    @JsonProperty("markPrice")
    private Double price;
    
}
