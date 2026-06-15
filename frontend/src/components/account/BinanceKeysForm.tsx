import {useState} from "react";
import "../../pages/account/SettingsPage.css";
import {API_URL} from "../../services/api.ts";

export const BinanceKeysForm = ({ onCancel }: { onCancel: () => void }) => {
    const [apiKey, setApiKey] = useState("");
    const [secretKey, setSecretKey] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            console.log("Saving Binance keys");
            const keys = {
                binanceApiKey: apiKey,
                binanceSecretKey: secretKey,
            };

            const response = await fetch(`${API_URL}/binance/keys`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify(keys)
            });

            if (!response.ok) {
                const responseData = await response.json().catch(() => null);
                console.log(responseData);

                throw new Error(responseData?.message || "Saving binance keys error.");
            }

            alert(`Saved keys!`);
            onCancel();

        } catch (err: any) {
            console.error(err);
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="binance-form">
            <div className="form-group">
                <label htmlFor="apiKey">API Key</label>
                <input
                    type="text"
                    id="apiKey"
                    value={apiKey}
                    onChange={(e) => setApiKey(e.target.value)}
                    placeholder="Enter Binance API Key"
                    required
                />
            </div>
            <div className="form-group">
                <label htmlFor="secretKey">Secret Key</label>
                <input
                    type="password"
                    id="secretKey"
                    value={secretKey}
                    onChange={(e) => setSecretKey(e.target.value)}
                    placeholder="Enter Binance Secret Key"
                    required
                />
            </div>

            {error && (
                <div className="error-message" style={{ color: "#ef4444", fontSize: "0.85rem", marginTop: "0.5rem" }}>
                    {error}
                </div>
            )}

            <div className="form-actions">
                <button
                    type="button"
                    className="cancel-btn"
                    onClick={onCancel}
                    disabled={loading}
                >
                    Cancel
                </button>
                <button
                    type="submit"
                    className="save-btn"
                    disabled={loading}
                >
                    {loading ? "Saving..." : "Save Keys"}
                </button>
            </div>
        </form>
    );
};