package com.tradingsystem.trading_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tradingsystem.trading_bot.utils.SignatureGenerator;

@Configuration
public class SecurityConfig {

    @Value("${binance.api.secret}")
    private String secretKey;

    @Bean
    public SignatureGenerator hmacSha256Generator(){
        return new SignatureGenerator(secretKey, "HmacSHA256");
    }

    @Bean
    public SignatureGenerator hmacSha512Generator(){
        return new SignatureGenerator(secretKey, "HmacSHA512");
    }
}
