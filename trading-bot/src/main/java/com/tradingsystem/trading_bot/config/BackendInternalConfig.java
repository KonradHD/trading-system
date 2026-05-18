package com.tradingsystem.trading_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;



public class BackendInternalConfig {
    
    @Value("${app.security.internal-api-secret}")
    private String internalSecret;

    @Bean
    public RequestInterceptor internalRequestInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("X-Internal-Secret", internalSecret);
        };
    }
}
