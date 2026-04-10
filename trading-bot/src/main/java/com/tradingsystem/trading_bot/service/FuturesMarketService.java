package com.tradingsystem.trading_bot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.FundingRateDTO;
import com.tradingsystem.trading_bot.dto.OpenInterestDTO;
import com.tradingsystem.trading_bot.model.FundingRateEntity;
import com.tradingsystem.trading_bot.model.OpenInterestEntity;
import com.tradingsystem.trading_bot.repository.FundingRateRepository;
import com.tradingsystem.trading_bot.repository.OpenInterestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FuturesMarketService {
    private final FundingRateRepository fundingRateRepository;
    private final OpenInterestRepository openInterestRepository;

    public void saveOpenInterest(OpenInterestDTO dto){
        OpenInterestEntity entity = mapOIToEntity(dto);
        openInterestRepository.save(entity);
    }

    public void saveFundingRate(FundingRateDTO dto){
        FundingRateEntity entity = mapFDToEntity(dto);
        fundingRateRepository.save(entity);
    }

    public void saveAllFundingRates(List<FundingRateDTO> rates){
        List<FundingRateEntity> entities = rates.stream()
                                                .map(this::mapFDToEntity)
                                                .toList();
        fundingRateRepository.saveAll(entities);
    }


    private OpenInterestEntity mapOIToEntity(OpenInterestDTO dto){
        return OpenInterestEntity.builder()
            .symbol(dto.getSymbol())
            .openInterest(dto.getOpenInterest())
            .timestamp(dto.getTimestamp())
            .build();
    }

    private FundingRateEntity mapFDToEntity(FundingRateDTO dto){
        return FundingRateEntity.builder()
            .symbol(dto.getSymbol())
            .fundingRate(dto.getFundingRate())
            .timestamp(dto.getTimestamp())
            .price(dto.getPrice())
            .build();
    }
}
