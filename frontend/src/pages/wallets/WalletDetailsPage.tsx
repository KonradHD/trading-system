import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { API_URL } from "../../services/api";
import { useAuth } from "../../services/AuthProvider.tsx";
import "./WalletDetailsPage.css";

type Inventory = {
    stockSymbol: string;
    quantity: number;
};

type WalletData = {
    id: number;
    name: string;
    activeTrades: boolean;
    inventories: Inventory[];
};

export const WalletDetailsPage = () => {
    const { id } = useParams();
    const { token } = useAuth();

    const [wallet, setWallet] = useState<WalletData | null>(null);
    const [botActive, setBotActive] = useState(false);
    const [loading, setLoading] = useState(true);
    const [isSwitching, setIsSwitching] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id || !token) return;

        setLoading(true);
        setError(null);

        fetch(`${API_URL}/wallet/${id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            credentials: "include"
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Portfel nie istnieje lub brak dostępu.");
                }
                return response.json();
            })
            .then((data: WalletData) => {
                setWallet(data);
                setBotActive(data.activeTrades);
            })
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, [id, token]);

    const switchBotStatus = () => {
        if (!id || isSwitching || error || !token) return;

        setIsSwitching(true);

        fetch(`${API_URL}/wallet/active/switch/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            credentials: "include"
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Nie udało się zmienić statusu bota");
                }
            })
            .then(() => {
                setBotActive((previous) => !previous);
            })
            .catch((error) => {
                console.error("Błąd przełączania bota:", error);
                setError("Nie udało się przełączyć statusu bota.");
            })
            .finally(() => setIsSwitching(false));
    };

    if (loading) {
        return <section className="wallet-details-state">Ładowanie portfela...</section>;
    }

    if (error) {
        return (
            <section className="wallet-details-state">
                <h1>Szczegóły portfela</h1>
                <p className="error-text">{error}</p>
            </section>
        );
    }

    return (
        <section className="wallet-details-container">
            <h1>{wallet?.name || `Portfel #${id}`}</h1>
            <p className="subtitle-text">Statystyki portfela, bilans oraz status bota.</p>

            <div className="wallet-details-grid">
                <div className="wallet-details-card">
                    <h2>Inwentarz krypto</h2>

                    {wallet?.inventories?.length ? (
                        wallet.inventories.map((item) => (
                            <div key={item.stockSymbol} className="inventory-item">
                                <span>{item.stockSymbol}</span>
                                <strong>{item.quantity}</strong>
                            </div>
                        ))
                    ) : (
                        <p className="empty-text">Brak aktywów na tym portfelu.</p>
                    )}
                </div>

                <div className="wallet-details-card">
                    <h2>Autonomiczny Bot</h2>

                    <p>Status: <strong className={botActive ? "status-active" : "status-inactive"}>
                        {botActive ? "Aktywny" : "Wstrzymany"}
                    </strong></p>

                    <button
                        onClick={switchBotStatus}
                        disabled={isSwitching}
                        className={`btn-bot ${botActive ? "btn-stop" : "btn-start"}`}
                    >
                        {isSwitching
                            ? "Przełączanie..."
                            : botActive
                                ? "Zatrzymaj bota"
                                : "Uruchom bota"}
                    </button>
                </div>
            </div>
        </section>
    );
};