import { useEffect, useState } from "react";
import API_URL from "../../services/api";

type Analytics = {
    totalTrades: number;
    buyTrades: number;
    sellTrades: number;
    totalVolume: number;
    averagePrice: number;
};

export const AnalyticsPage = () => {
    const [analytics, setAnalytics] = useState<Analytics | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch(`${API_URL}/analytics`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch analytics");
                }

                return response.json();
            })
            .then((data) => setAnalytics(data))
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) {
        return <section style={{ padding: "2rem" }}>Loading analytics...</section>;
    }

    if (error) {
        return (
            <section style={{ padding: "2rem" }}>
                <h1>Analytics</h1>
                <p>{error}</p>
            </section>
        );
    }

    const hasTrades = (analytics?.totalTrades ?? 0) > 0;

    return (
        <section style={{ padding: "2rem" }}>
            <h1>Analytics</h1>
            <p>Trading strategy statistics from backend database.</p>

            {!hasTrades && (
                <div style={{
                    padding: "1rem",
                    border: "1px solid #ddd",
                    borderRadius: "10px",
                    marginTop: "1.5rem"
                }}>
                    <h2>No trading data yet</h2>
                    <p>
                        Analytics endpoint is working, but there are no saved transactions in the database yet.
                    </p>
                </div>
            )}

            <div style={{ display: "grid", gap: "1rem", marginTop: "1.5rem" }}>
                <div style={{ padding: "1rem", border: "1px solid #ddd", borderRadius: "10px" }}>
                    <h2>Trades</h2>
                    <p>Total trades: {analytics?.totalTrades ?? 0}</p>
                    <p>BUY signals: {analytics?.buyTrades ?? 0}</p>
                    <p>SELL signals: {analytics?.sellTrades ?? 0}</p>
                </div>

                <div style={{ padding: "1rem", border: "1px solid #ddd", borderRadius: "10px" }}>
                    <h2>Volume</h2>
                    <p>Total volume: {analytics?.totalVolume ?? 0}</p>
                    <p>Average price: {analytics?.averagePrice ?? 0}</p>
                </div>
            </div>
        </section>
    );
};