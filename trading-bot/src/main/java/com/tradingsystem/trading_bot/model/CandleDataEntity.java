package com.tradingsystem.trading_bot.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candle_data")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CandleDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double closePrice;
    private Double volume;
    private Long openTime;
    private Long closeTime;
    private Boolean isClosed;
}