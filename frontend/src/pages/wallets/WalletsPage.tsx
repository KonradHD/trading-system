import { useAuth } from "../../services/AuthProvider.tsx";
import { useEffect, useState } from "react";
import type { Wallet } from "../../services/Types.ts";
import { API_URL } from "../../services/api.ts";
import { Link } from "react-router-dom";
import "./WalletsPage.css";

export const WalletsPage = () => {
    const { token } = useAuth();
    const [wallets, setWallets] = useState<Wallet[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [newWalletName, setNewWalletName] = useState("");
    const [newWalletFunds, setNewWalletFunds] = useState("");
    const [isActiveForBot, setIsActiveForBot] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (!token) return;

        fetch(`${API_URL}/wallet/user`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            credentials: "include"
        })
            .then((response) => response.json())
            .then((data) => {
                if (Array.isArray(data.wallets)) {
                    setWallets(data.wallets)
                    console.log("dasdad");
                }
                else setWallets([]);
                console.log(data.wallets);
            })
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, [token]);

    const handleCreateWallet = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsSubmitting(true);

        try {
            const response = await fetch(`${API_URL}/wallet`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    name: newWalletName,
                    availableFunds: parseFloat(newWalletFunds),
                    activeTrades: isActiveForBot
                }),
                credentials: "include"
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                throw new Error(errorData?.message || "Nie udało się utworzyć portfela.");
            }

            const createdWallet = await response.json();

            setWallets([...wallets, createdWallet]);

            setIsModalOpen(false);
            setNewWalletName("");
            setNewWalletFunds("");
            setIsActiveForBot(false);

        } catch (err: any) {
            alert(err.message);
        } finally {
            setIsSubmitting(false);
        }
    };

    if (loading) return <section className="wallets-loading-state"><div className="spinner"></div></section>;
    if (error) return <section className="wallets-error-state"><p>{error}</p></section>;

    return (
        <section className="wallets-container">
            <header className="wallets-header">
                <h2>Zarządzanie Kapitałem</h2>
            </header>

            {wallets.length === 0 ? (
                <div className="wallets-empty">
                    <p>Nie masz jeszcze żadnych skonfigurowanych portfeli.</p>
                    <button className="btn-primary" onClick={() => setIsModalOpen(true)}>
                        + Nowy portfel
                    </button>
                </div>
            ) : (
                <div className="wallets-grid">
                    {wallets.map((wallet) => (
                        <div key={wallet.walletId} className="wallet-card">
                            <div className="wallet-card-header">
                                <h3>{wallet.walletName}</h3>
                                <span className={`status-badge ${wallet.isActive ? 'active' : 'inactive'}`}>
                                    {wallet.isActive ? 'Aktywny' : 'Nieaktywny'}
                                </span>
                            </div>

                            <div className="wallet-card-body">
                                <div className="wallet-stat">
                                    <span className="stat-label">Dostępne środki</span>
                                    <span className="stat-value highlight">
                                        {Number(wallet.availableFunds || 0).toLocaleString('en-US', {
                                            minimumFractionDigits: 2,
                                            maximumFractionDigits: 2
                                        })} USDT
                                    </span>
                                </div>
                                <div className="wallet-stat">
                                    <span className="stat-label">Zarządzany przez bota</span>
                                    <span className="stat-value">
                                        {wallet.activeTrades ? 'Tak' : 'Nie'}
                                    </span>
                                </div>
                            </div>

                            <div className="wallet-card-footer">
                                <Link to={`${wallet.walletId}`} className="btn-secondary">
                                    Szczegóły i historia
                                </Link>
                            </div>
                        </div>
                    ))}
                    <button className="btn-primary" onClick={() => setIsModalOpen(true)}>
                        + Nowy portfel
                    </button>
                </div>
            )}

            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h3>Utwórz nowy portfel</h3>
                        <form onSubmit={handleCreateWallet}>
                            <div className="form-group">
                                <label>Nazwa portfela</label>
                                <input
                                    type="text"
                                    value={newWalletName}
                                    onChange={(e) => setNewWalletName(e.target.value)}
                                    placeholder="np. Portfel Główny USDT"
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label>Początkowe środki (USDT)</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    min="0"
                                    value={newWalletFunds}
                                    onChange={(e) => setNewWalletFunds(e.target.value)}
                                    placeholder="0.00"
                                    required
                                />
                            </div>

                            <div className="form-group-checkbox">
                                <label>
                                    <input
                                        type="checkbox"
                                        checked={isActiveForBot}
                                        onChange={(e) => setIsActiveForBot(e.target.checked)}
                                    />
                                    Zezwól botom na handel na tym portfelu
                                </label>
                            </div>

                            <div className="modal-actions">
                                <button
                                    type="button"
                                    className="btn-secondary"
                                    onClick={() => setIsModalOpen(false)}
                                    disabled={isSubmitting}
                                >
                                    Anuluj
                                </button>
                                <button
                                    type="submit"
                                    className="btn-primary"
                                    disabled={isSubmitting}
                                >
                                    {isSubmitting ? "Tworzenie..." : "Utwórz portfel"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </section>
    );
};