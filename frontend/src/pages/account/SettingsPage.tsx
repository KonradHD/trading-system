import { useState } from "react";
import "./SettingsPage.css";
import { BinanceKeysForm } from "../../components/account/BinanceKeysForm.tsx";

export const SettingsPage = () => {
    const [showBinanceForm, setShowBinanceForm] = useState(false);

    return (
        <section className="settings-page">
            <h1 className="page-title">Settings</h1>
            <p className="page-desc">Manage your account preferences and integrations.</p>

            <div className="settings-grid">

                <div className="data-card">
                    <h2 className="card-title">General Preferences</h2>
                    <div className="card-content">
                        <div className="setting-row">
                            <div className="setting-info">
                                <strong>Email Notifications</strong>
                                <span>Receive trade alerts and summaries.</span>
                            </div>
                            <label className="switch">
                                <input type="checkbox" defaultChecked />
                                <span className="slider"></span>
                            </label>
                        </div>
                        <div className="setting-row">
                            <div className="setting-info">
                                <strong>Two-Factor Authentication (2FA)</strong>
                                <span>Secure your account with an extra layer of protection.</span>
                            </div>
                            <label className="switch">
                                <input type="checkbox" />
                                <span className="slider"></span>
                            </label>
                        </div>
                    </div>
                </div>

                <div className="data-card">
                    <h2 className="card-title">Exchange Integrations</h2>
                    <div className="card-content">
                        <div className="setting-row">
                            <div className="setting-info">
                                <strong>Binance API</strong>
                                <span>Connect your account for automated trading.</span>
                            </div>
                            {!showBinanceForm && (
                                <button
                                    className="action-btn"
                                    onClick={() => setShowBinanceForm(true)}
                                >
                                    Connect
                                </button>
                            )}
                        </div>

                        {showBinanceForm && (
                            <div className="integration-wrapper">
                                <BinanceKeysForm onCancel={() => setShowBinanceForm(false)} />
                            </div>
                        )}
                    </div>
                </div>

            </div>
        </section>
    );
};