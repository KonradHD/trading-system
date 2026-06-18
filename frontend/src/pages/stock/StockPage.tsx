import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { API_URL } from "../../services/api";
import "./StockPage.css";

type StockSymbol = {
    symbol: string;
};

export const StockPage = () => {
    const [symbols, setSymbols] = useState<StockSymbol[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`${API_URL}/stock`)
            .then((response) => response.json())
            .then((data) => setSymbols(data))
            .catch((error) => console.error("Failed to fetch stock symbols", error))
            .finally(() => setLoading(false));
    }, []);

    if (loading) {
        return <section className="stock-loading-state">Loading stock market...</section>;
    }

    return (
        <section className="stock-page">
            <h1 className="page-title">Stock market</h1>
            <p className="page-desc">List of available cryptocurrency markets from backend database.</p>

            <div className="symbols-grid">
                {symbols.map((item) => (
                    <Link
                        key={item.symbol}
                        to={`${item.symbol}`}
                        className="symbol-card"
                    >
                        <strong className="symbol-name">{item.symbol}</strong>
                        <span className="symbol-action">Open market details <span>→</span></span>
                    </Link>
                ))}
            </div>
        </section>
    );
};