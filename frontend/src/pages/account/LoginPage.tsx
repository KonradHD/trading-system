import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AUTH_URL } from "../../services/api";
import { useEffect } from "react";
import { UAParser } from "ua-parser-js";
// import { handleLogout } from "../services/api";
// import type { TokenPayload } from "../components/Types";
// import { getUserFromToken } from "../validationRules";
import './LoginPage.css';


export default function LoginPage() {
    const navigate = useNavigate();

    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [deviceInfo, setDeviceInfo] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    // const [userInfo, setUserInfo] = useState<TokenPayload | null>(getUserFromToken());

    // useEffect(() => {
    //     if (userInfo) {
    //         handleLogout();
    //     }
    // }, [])

    useEffect(() => {
        const parser = new UAParser();
        const result = parser.getResult();
        const browser = `${result.browser.name} ${result.browser.version}`;
        const os = `${result.os.name} ${result.os.version}`;
        const device = result.device.type ? result.device.type : "desktop";
        const cleanDeviceInfo = `${browser} on ${os} (${device})`;

        setDeviceInfo(cleanDeviceInfo);
    }, []);


    async function handleLogin(event: React.FormEvent) {
        event.preventDefault();
        setError(null);
        setLoading(true);

        try {

            const response = await fetch(`${AUTH_URL}/login`, {
                method: "POST",
                credentials: "include",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ login: login, password: password, deviceInfo: deviceInfo }),
            });

            let responseData;

            if (!response.ok) {
                responseData = await response.json();

                if (!responseData.success) {
                    throw new Error(responseData.message);
                }
                throw new Error("Login error");
            }

            responseData = await response.json();

            if (!responseData.accessToken) {
                throw new Error("Invalid access token.");
            }

            alert(`Approved singing in!`);
            localStorage.setItem('token', responseData.accessToken);
            navigate("/home");

        } catch (err: any) {
            console.log(err);
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    return (
        <section className="login-page">
            <div className="login-card">
                <h2>
                    Signing in:
                </h2>

                <form onSubmit={handleLogin} className="login-form">

                    <div className="form-group">
                        <label htmlFor="login">Login: </label>
                        <input
                            id="login"
                            type="text"
                            value={login}
                            onChange={(e) => setLogin(e.target.value)}
                            placeholder="e.g. WojferPL"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password: </label>
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="******"
                            required
                        />
                    </div>

                    {/* wyświetlanie błędów */}
                    {error && <div style={{ color: "#ef4444", marginBottom: "10px", fontSize: "0.9rem" }}>{error}</div>}

                    <button type="submit" disabled={loading} className="login-btn">
                        {loading ? "Signing in..." : "Sign in"}
                    </button>
                </form>

                <button
                    onClick={() => navigate("/")}
                    className="back-btn"
                >
                    &larr; Back to default page
                </button>
            </div>
        </section>
    );
}