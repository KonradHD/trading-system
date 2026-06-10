package com.tradingsystem.trading_bot.analyser;

import com.tradingsystem.trading_bot.event.TradeSignalEvent;
import com.tradingsystem.trading_bot.model.CandleDataEntity;
import com.tradingsystem.trading_bot.repository.CandleDataRepository;
import com.tradingsystem.trading_bot.utils.MarketActions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingStrategy {

    private static final int CANDLES_LIMIT = 80;
    private static final int EMA_FAST = 12;
    private static final int EMA_SLOW = 26;
    private static final int RSI_PERIOD = 14;

    private static final double MIN_RSI_FOR_BUY = 45.0;
    private static final double MAX_RSI_FOR_BUY = 70.0;
    private static final double MIN_RSI_FOR_SELL = 30.0;
    private static final double MAX_RSI_FOR_SELL = 55.0;

    private final CandleDataRepository candleDataRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void analyze(String symbol) {
        List<CandleDataEntity> candles = candleDataRepository.findRecentCandles(symbol, CANDLES_LIMIT);

        if (candles.size() < CANDLES_LIMIT) {
            log.info("STRATEGY HOLD {} | not enough candles: {}/{}", symbol, candles.size(), CANDLES_LIMIT);
            return;
        }

        candles = candles.stream()
                .sorted(Comparator.comparing(CandleDataEntity::getCloseTime))
                .toList();

        List<Double> closes = candles.stream()
                .map(candle -> candle.getClosePrice().doubleValue())
                .toList();

        double currentPrice = closes.get(closes.size() - 1);
        double previousPrice = closes.get(closes.size() - 2);

        double emaFast = calculateEma(closes, EMA_FAST);
        double emaSlow = calculateEma(closes, EMA_SLOW);
        double rsi = calculateRsi(closes, RSI_PERIOD);

        boolean uptrend = emaFast > emaSlow;
        boolean downtrend = emaFast < emaSlow;
        boolean priceRising = currentPrice > previousPrice;
        boolean priceFalling = currentPrice < previousPrice;

        log.info(
                "STRATEGY CHECK {} | price={} prev={} ema{}={} ema{}={} rsi={} trend={}",
                symbol,
                round(currentPrice),
                round(previousPrice),
                EMA_FAST,
                round(emaFast),
                EMA_SLOW,
                round(emaSlow),
                round(rsi),
                uptrend ? "UP" : downtrend ? "DOWN" : "FLAT"
        );

        if (uptrend && priceRising && rsi > MIN_RSI_FOR_BUY && rsi < MAX_RSI_FOR_BUY) {
            publishSignal(
                    symbol,
                    MarketActions.BUY,
                    "BUY: EMA12 > EMA26, RSI in safe range, price is rising"
            );
            return;
        }

        if (downtrend && priceFalling && rsi > MIN_RSI_FOR_SELL && rsi < MAX_RSI_FOR_SELL) {
            publishSignal(
                    symbol,
                    MarketActions.SELL,
                    "SELL: EMA12 < EMA26, RSI in safe range, price is falling"
            );
            return;
        }

        log.info("STRATEGY HOLD {} | conditions not met", symbol);
    }

    private void publishSignal(String symbol, MarketActions action, String reason) {
        BigDecimal quantity = BigDecimal.valueOf(0.001);

        TradeSignalEvent signal = new TradeSignalEvent(
                action,
                symbol,
                quantity,
                reason,
                Instant.now()
        );

        log.warn(
                "STRATEGY SIGNAL {} {} | quantity={} | reason={}",
                action,
                symbol,
                quantity,
                reason
        );

        // Bezpieczny tryb projektu:
        // sygnał jest tylko logowany, nie wykonuje realnego zlecenia.
        //
        // Jeśli prowadzący chce pełny przepływ eventów, odkomentuj:
        // eventPublisher.publishEvent(signal);
    }

    private double calculateEma(List<Double> values, int period) {
        double multiplier = 2.0 / (period + 1.0);
        double ema = simpleMovingAverage(values.subList(0, period));

        for (int i = period; i < values.size(); i++) {
            ema = ((values.get(i) - ema) * multiplier) + ema;
        }

        return ema;
    }

    private double simpleMovingAverage(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private double calculateRsi(List<Double> values, int period) {
        if (values.size() <= period) {
            return 50.0;
        }

        double gains = 0.0;
        double losses = 0.0;

        for (int i = values.size() - period; i < values.size(); i++) {
            double change = values.get(i) - values.get(i - 1);

            if (change > 0) {
                gains += change;
            } else {
                losses += Math.abs(change);
            }
        }

        if (losses == 0.0) {
            return 100.0;
        }

        double relativeStrength = gains / losses;
        return 100.0 - (100.0 / (1.0 + relativeStrength));
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}