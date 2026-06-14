import { useEffect, useState } from "react";
import { API_URL } from "../../services/api";
import "./AnalyticsPage.css";

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
        return <section className="analytics-loading-state">Loading analytics...</section>;
    }

    if (error) {
        return (
            <section className="analytics-page">
                <h1 className="page-title">Analytics</h1>
                <div className="error-card">
                    <p>{error}</p>
                </div>
            </section>
        );
    }

    const hasTrades = (analytics?.totalTrades ?? 0) > 0;

    return (
        <section className="analytics-page">
            <h1 className="page-title">Analytics</h1>
            <p className="page-desc">Trading strategy statistics from backend database.</p>

            {!hasTrades && (
                <div className="data-card empty-state-card">
                    <h2 className="card-title">No trading data yet</h2>
                    <div className="card-content">
                        <p>Analytics endpoint is working, but there are no saved transactions in the database yet.</p>
                    </div>
                </div>
            )}

            <div className="analytics-grid">
                <div className="data-card">
                    <h2 className="card-title">Trades</h2>
                    <div className="card-content">
                        <p><span>Total trades:</span> <strong>{analytics?.totalTrades ?? 0}</strong></p>
                        <p><span>BUY signals:</span> <strong>{analytics?.buyTrades ?? 0}</strong></p>
                        <p><span>SELL signals:</span> <strong>{analytics?.sellTrades ?? 0}</strong></p>
                    </div>
                </div>

                <div className="data-card">
                    <h2 className="card-title">Volume</h2>
                    <div className="card-content">
                        <p><span>Total volume:</span> <strong>{analytics?.totalVolume ?? 0}</strong></p>
                        <p><span>Average price:</span> <strong>${analytics?.averagePrice ?? 0}</strong></p>
                    </div>
                </div>
            </div>
        </section>
    );
};