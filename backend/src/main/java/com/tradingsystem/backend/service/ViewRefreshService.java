package com.tradingsystem.backend.service;

import java.time.Instant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ViewRefreshService {

    private final JdbcTemplate jdbcTemplate;

    public ViewRefreshService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // co 5 minut
    @Scheduled(cron = "0 */5 * * * *")
    public void refreshWalletStatistics() {
        log.info("Refreshing wallet statistics at {}", Instant.now());
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY wallet_statistics");
    }
}