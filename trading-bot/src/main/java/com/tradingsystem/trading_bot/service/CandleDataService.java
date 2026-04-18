package com.tradingsystem.trading_bot.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.model.CandleDataEntity;
import com.tradingsystem.trading_bot.repository.CandleDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandleDataService {
    private final CandleDataRepository candleDataRepository;

    public void saveCandle(CandleDTO dto) {
        CandleDataEntity entity = mapToEntity(dto);
        candleDataRepository.save(entity);
    }

    public void saveAllCandles(List<CandleDTO> dtos) {
        List<CandleDataEntity> entities = dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        candleDataRepository.saveAll(entities);
    }

    private CandleDataEntity mapToEntity(CandleDTO dto) {
        return CandleDataEntity.builder()
                .symbol(dto.getSymbol())
                .openPrice(dto.getOpen())
                .highPrice(dto.getHigh())
                .lowPrice(dto.getLow())
                .closePrice(dto.getClose())
                .volume(dto.getVolume())
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .isClosed(dto.getIsClosed())
                .build();
    }

}
