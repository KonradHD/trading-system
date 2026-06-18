package com.tradingsystem.trading_bot.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.components.BinanceDisposableHttpClient;
import com.tradingsystem.trading_bot.components.BinanceScheduledHttpClient;
import com.tradingsystem.trading_bot.components.BinanceWebSocketClient;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinanceConnectionService implements CommandLineRunner {

    private final BinanceWebSocketClient socketClient;
    private final BinanceDisposableHttpClient disposableHttpClient;
    private final List<BinanceScheduledHttpClient> httpClients;

    @Override
    public void run(String... args) {
        log.info("Binance connection service is starting...");
        List<String> cryptoSymbols = Arrays.asList(
                "btcusdt", "ethusdt", "bnbusdt", "solusdt", "xrpusdt",
                "adausdt", "dogeusdt", "avaxusdt", "linkusdt", "dotusdt",
                "ltcusdt", "shibusdt", "trxusdt", "uniusdt", "atomusdt",
                "etcusdt", "xlmusdt", "nearusdt", "aptusdt", "filusdt"
        );

        for (String symbol : cryptoSymbols) {
            log.info("Inicjalizacja danych i strumienia dla symbolu: {}", symbol);

            URI historicalCandlesUrl = BinanceAPIUrlBuilder.httpCandleEndpoint(symbol, "1m", 100);
            URI websocketCandlesUrl = BinanceAPIUrlBuilder.streamCandleEndpoint(symbol, "1m");

            try {
                disposableHttpClient.historicalCandles(historicalCandlesUrl);
                socketClient.connect(websocketCandlesUrl);
            } catch (Exception e) {
                log.error("Wystąpił błąd podczas inicjalizacji dla symbolu {}: {}", symbol, e.getMessage());
            }
        }

        log.info("Zakończono pobieranie świec historycznych i podłączanie strumieni WebSocket dla wszystkich {} symboli.", cryptoSymbols.size());
    }

    @Scheduled(fixedRateString = "${cyclic.http.time}")
    public void triggerHttpDataFetch() {
        log.info("Fetching scheduled data using HTTP.");

        for (BinanceScheduledHttpClient client : httpClients) {
            client.fetchData();
        }
    }
}