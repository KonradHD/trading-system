import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
    AreaChart,
    Area,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer
} from "recharts";
import { API_URL } from "../../services/api";
import "./StockDetailsPage.css";

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

type Period = "current" | "day" | "week" | "month";

export const StockDetailsPage = () => {
    const { symbol } = useParams();
    const [candles, setCandles] = useState<Candle[]>([]);
    const [loading, setLoading] = useState(true);

    const [period, setPeriod] = useState<Period>("day");

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
        return <section className="stock-loading-state">Loading market data...</section>;
    }

    const getSliceCount = () => {
        switch (period) {
            case "current": return 10;
            case "day": return 24;
            case "week": return 24 * 7;
            case "month": return 24 * 30;
            default: return 30;
        }
    };

    const chartData = [...candles]
        .slice(0, getSliceCount())
        .reverse()
        .map((c) => {
            const date = new Date(c.closeTime);

            let formattedTime = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            if (period === "week" || period === "month") {
                formattedTime = date.toLocaleDateString([], { month: 'short', day: 'numeric' });
            }

            return {
                time: formattedTime,
                close: c.closePrice,
                high: c.highPrice,
                low: c.lowPrice
            };
        });

    return (
        <section className="stock-details-page">
            <Link to="/stock" className="back-link">← Back to stock list</Link>

            <h1 className="page-title">{symbol?.substring(0, symbol.length - 4)}</h1>
            <p className="page-desc">Cryptocurrency market details from backend database.</p>

            <div className="data-card">
                <h2 className="card-title">Market data</h2>
                <div className="card-content">
                    <p>Symbol: <strong>{symbol?.substring(0, symbol.length - 4)}</strong></p>
                    <p>Current price: <strong>{latestCandle ? `${latestCandle.closePrice} USDT` : "No data"}</strong></p>
                    <p>Volume: <strong>{latestCandle ? latestCandle.volume : "No data"}</strong></p>
                </div>
            </div>

            <div className="data-card chart-card">
                <div className="chart-header" style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "1.5rem" }}>
                    <h2 className="card-title" style={{ marginBottom: 0 }}>Price Chart (Close)</h2>

                    <div className="period-selector">
                        {(["current", "day", "week", "month"] as Period[]).map((p) => (
                            <button
                                key={p}
                                className={`period-btn ${period === p ? "active" : ""}`}
                                onClick={() => setPeriod(p)}
                            >
                                {p.toUpperCase()}
                            </button>
                        ))}
                    </div>
                </div>

                <div className="chart-wrapper">
                    <ResponsiveContainer width="100%" height="100%">
                        <AreaChart data={chartData} margin={{ top: 10, right: 0, left: 0, bottom: 0 }}>
                            <defs>
                                <linearGradient id="colorClose" x1="0" y1="0" x2="0" y2="1">
                                    <stop offset="5%" stopColor="var(--accent)" stopOpacity={0.4} />
                                    <stop offset="95%" stopColor="var(--accent)" stopOpacity={0} />
                                </linearGradient>
                            </defs>

                            <CartesianGrid strokeDasharray="3 3" stroke="var(--border)" vertical={false} />

                            <XAxis
                                dataKey="time"
                                stroke="var(--text)"
                                tick={{ fill: "var(--text)", fontSize: 12 }}
                                tickMargin={10}
                                minTickGap={20}
                            />
                            <YAxis
                                domain={["auto", "auto"]}
                                stroke="var(--text)"
                                tick={{ fill: "var(--text)", fontSize: 12 }}
                                tickFormatter={(value) => `$${value}`}
                                width={80}
                            />

                            <Tooltip
                                contentStyle={{
                                    backgroundColor: "var(--bg)",
                                    borderColor: "var(--border)",
                                    borderRadius: "8px",
                                    color: "var(--text-h)"
                                }}
                                itemStyle={{ color: "var(--accent)", fontWeight: "bold" }}
                                labelStyle={{ color: "var(--text)", marginBottom: "5px" }}
                            />

                            <Area
                                type="monotone"
                                dataKey="close"
                                stroke="var(--accent)"
                                strokeWidth={3}
                                fillOpacity={1}
                                fill="url(#colorClose)"
                                name="Close Price"
                            />
                        </AreaChart>
                    </ResponsiveContainer>
                </div>
            </div>
        </section>
    );
};