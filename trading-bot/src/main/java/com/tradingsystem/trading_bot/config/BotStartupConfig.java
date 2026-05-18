package com.tradingsystem.trading_bot.config;

import com.tradingsystem.trading_bot.service.StartupService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BotStartupConfig {
    
    @Bean
    public CommandLineRunner fetchActiveUsers(StartupService startupService){
        
        return args -> {
            startupService.initializeActiveUsers();
        };
    }
}
