import { useState } from "react";
import { PASSWORD_RULES } from "../../services/validationRules";
import '../../pages/account/RegisterPage.css';

type CredentialsProps = {
    login: string;
    setLogin: (login: string) => void;
    password: string;
    setPassword: (password: string) => void;
    confirmPassword: string;
    setConfirmPassword: (pass: string) => void;
    loginError: boolean,
}

export default function CredentialsForm({ login, setLogin, password, setPassword, confirmPassword, setConfirmPassword, loginError }: CredentialsProps) {

    const [touched, setTouched] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmedPassword, setShowConfirmedPassword] = useState(false);

    const passwordsMatch = password === confirmPassword && password.length > 0;

    return (
        <div className="credentials">

            <div className="form-group">
                <label>Login:</label>
                <input
                    type="text"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                    className="std-input"
                    required
                />
                {
                    loginError &&
                    <span className="error-message">Given login already exists.</span>
                }
            </div>

            {/* HASŁO */}
            <div className="form-group password-group">
                <label>Password: </label>
                <div className="input-wrapper">
                    <input
                        type={showPassword ? "text" : "password"}
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value);
                            setTouched(true);
                        }}
                        className="std-input password-input"
                        style={{
                            borderColor: touched && PASSWORD_RULES.some(v => !v.rule(password)) ? 'red' : undefined
                        }}
                        required
                    />
                    <button
                        type="button"
                        onClick={() => setShowPassword(!showPassword)}
                        className="password-toggle-btn"
                        aria-label={showPassword ? "Ukryj hasło" : "Pokaż hasło"}
                    >
                        {showPassword ? (
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
                                <line x1="1" y1="1" x2="23" y2="23"></line>
                            </svg>
                        ) : (
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                                <circle cx="12" cy="12" r="3"></circle>
                            </svg>
                        )}
                    </button>
                </div>

                {touched && (
                    <ul style={{ listStyle: "none", padding: 0, fontSize: "0.70rem", marginBottom: "10px" }}>
                        {PASSWORD_RULES.map((val) => {
                            const isValid = val.rule(password);
                            return (
                                <li key={val.id} style={{ color: isValid ? "green" : "red", display: 'flex', alignItems: 'center', gap: '5px' }}>
                                    {isValid ? "✅" : "❌"} {val.text}
                                </li>
                            );
                        })}
                    </ul>
                )}
            </div>

            <div className="form-group password-group">
                <label>Confirm password: </label>
                <div className="input-wrapper">
                    <input
                        type={showConfirmedPassword ? "text" : "password"}
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        className="std-input password-input"
                        required
                    />
                    <button
                        type="button"
                        onClick={() => setShowConfirmedPassword(!showConfirmedPassword)}
                        className="password-toggle-btn"
                        aria-label={showConfirmedPassword ? "Ukryj hasło" : "Pokaż hasło"}
                    >
                        {showConfirmedPassword ? (
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
                                <line x1="1" y1="1" x2="23" y2="23"></line>
                            </svg>
                        ) : (
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                                <circle cx="12" cy="12" r="3"></circle>
                            </svg>
                        )}
                    </button>
                </div>
                {confirmPassword.length > 0 && (
                    <div style={{ fontSize: "0.85rem", marginTop: "5px", color: passwordsMatch ? "green" : "red" }}>
                        {passwordsMatch ? "Hasła są zgodne" : "Hasła nie pasują do siebie"}
                    </div>
                )}
            </div>
        </div>
    );
}