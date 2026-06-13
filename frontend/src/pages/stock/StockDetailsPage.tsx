import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import API_URL from "../../services/api";

type Candle = {
    symbol: string;
    openPrice: number;
    highPrice: number;
    lowPrice: number;
    closePrice: number;
    volume: number;
    openTime: number;
    closeTime: number;
};

export const StockDetailsPage = () => {
    const { symbol } = useParams();
    const [candles, setCandles] = useState<Candle[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!symbol) return;

        fetch(`${API_URL}/stock/${symbol}`)
            .then((response) => response.json())
            .then((data) => setCandles(data))
            .catch((error) => console.error("Failed to fetch candles", error))
            .finally(() => setLoading(false));
    }, [symbol]);

    const latestCandle = candles[0];

    if (loading) {
        return <section style={{ padding: "2rem" }}>Loading market data...</section>;
    }

    return (
        <section style={{ padding: "2rem" }}>
            <Link to="/stock">← Back to stock list</Link>

            <h1>{symbol}</h1>
            <p>Cryptocurrency market details from backend database.</p>

            <div style={{ marginTop: "1.5rem", padding: "1.5rem", border: "1px solid #ddd", borderRadius: "10px" }}>
                <h2>Market data</h2>
                <p>Symbol: <strong>{symbol}</strong></p>
                <p>Current price: {latestCandle ? `${latestCandle.closePrice} USDT` : "No data"}</p>
                <p>Volume: {latestCandle ? latestCandle.volume : "No data"}</p>
            </div>

            <div style={{ marginTop: "1.5rem", padding: "1rem", border: "1px solid #ddd", borderRadius: "10px" }}>
                <h2>Recent candles</h2>
                {candles.slice(0, 10).map((candle) => (
                    <p key={candle.closeTime}>
                        Close: {candle.closePrice} | High: {candle.highPrice} | Low: {candle.lowPrice}
                    </p>
                ))}
            </div>
        </section>
    );
};