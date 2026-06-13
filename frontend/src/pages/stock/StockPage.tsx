import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import API_URL from "../../services/api";

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
        return <section style={{ padding: "2rem" }}>Loading stock market...</section>;
    }

    return (
        <section style={{ padding: "2rem" }}>
            <h1>Stock market</h1>
            <p>List of available cryptocurrency markets from backend database.</p>

            <div style={{ display: "grid", gap: "1rem", marginTop: "1.5rem" }}>
                {symbols.map((item) => (
                    <Link
                        key={item.symbol}
                        to={`/stock/${item.symbol}`}
                        style={{
                            padding: "1rem",
                            border: "1px solid #ddd",
                            borderRadius: "10px",
                            textDecoration: "none",
                            color: "inherit",
                        }}
                    >
                        <strong>{item.symbol}</strong>
                        <p>Open market details</p>
                    </Link>
                ))}
            </div>
        </section>
    );
};