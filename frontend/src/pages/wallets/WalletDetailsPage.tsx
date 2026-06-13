import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import API_URL from "../../services/api";

type Inventory = {
    symbol: string;
    quantity: number;
};

type WalletData = {
    walletId: number;
    inventories: Inventory[];
};

export const WalletDetailsPage = () => {
    const { id } = useParams();

    const [wallet, setWallet] = useState<WalletData | null>(null);
    const [botActive, setBotActive] = useState(false);
    const [loading, setLoading] = useState(true);
    const [isSwitching, setIsSwitching] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id) return;

        setLoading(true);
        setError(null);

        fetch(`${API_URL}/wallet/${id}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Wallet does not exist or is inactive.");
                }

                return response.json();
            })
            .then((data) => setWallet(data))
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, [id]);

    const switchBotStatus = () => {
        if (!id || isSwitching || error) return;

        setIsSwitching(true);

        fetch(`${API_URL}/wallet/active/switch/${id}`, {
            method: "PUT",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to switch bot status");
                }

                return response.json();
            })
            .then((data) => {
                console.log("Switch bot response:", data);
                setBotActive((previous) => !previous);
            })
            .catch((error) => {
                console.error("Failed to switch bot status", error);
                setError("Failed to switch bot status.");
            })
            .finally(() => setIsSwitching(false));
    };

    if (loading) {
        return <section style={{ padding: "2rem" }}>Loading wallet...</section>;
    }

    if (error) {
        return (
            <section style={{ padding: "2rem" }}>
                <h1>Wallet #{id}</h1>
                <p>{error}</p>
            </section>
        );
    }

    return (
        <section style={{ padding: "2rem" }}>
            <h1>Wallet #{id}</h1>
            <p>Wallet statistics, balance and bot status.</p>

            <div style={{ display: "grid", gap: "1rem", marginTop: "1.5rem" }}>
                <div style={{ padding: "1rem", border: "1px solid #ddd", borderRadius: "10px" }}>
                    <h2>Inventory</h2>

                    {wallet?.inventories?.length ? (
                        wallet.inventories.map((item) => (
                            <p key={item.symbol}>
                                {item.symbol}: {item.quantity}
                            </p>
                        ))
                    ) : (
                        <p>No inventory data.</p>
                    )}
                </div>

                <div style={{ padding: "1rem", border: "1px solid #ddd", borderRadius: "10px" }}>
                    <h2>Trading bot</h2>

                    <p>Status: {botActive ? "active" : "inactive"}</p>

                    <button onClick={switchBotStatus} disabled={isSwitching}>
                        {isSwitching
                            ? "Switching..."
                            : botActive
                                ? "Disable bot"
                                : "Enable bot"}
                    </button>
                </div>
            </div>
        </section>
    );
};