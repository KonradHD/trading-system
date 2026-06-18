import {useState, useEffect, type ReactNode, createContext, useContext} from 'react';
import { AUTH_URL } from "./api.ts";
import type {AuthContextType} from "./Types.ts";

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        const silentRefresh = async () => {
            try {
                const response = await fetch(`${AUTH_URL}/refresh`, {
                    method: 'POST',
                    credentials: 'include'
                });

                if (response.ok) {
                    const data = await response.json();
                    setToken(data.accessToken);
                }
            } catch (error) {
                console.log("Brak aktywnej sesji. Użytkownik musi się zalogować.");
            } finally {
                setIsLoading(false);
            }
        };

        silentRefresh();
    }, []);

    return (
        <AuthContext.Provider value={{
            token,
            setAuthToken: setToken,
            isAuthenticated: !!token,
            isLoading
        }}>
            {children}
        </AuthContext.Provider>
    );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth musi być użyty wewnątrz AuthProvider");
    }
    return context;
};
