import { useEffect, useState } from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { getAccessToken, refreshTokenFetch, handleLogout } from "../services/api";

export const ProtectedRoute = () => {
    const location = useLocation();

    const [checking, setChecking] = useState(true);
    const [allowed, setAllowed] = useState(false);

    useEffect(() => {
        const checkSession = async () => {
            const token = getAccessToken();

            if (token) {
                setAllowed(true);
                setChecking(false);
                return;
            }

            const refreshResult = await refreshTokenFetch();

            if (refreshResult.success) {
                setAllowed(true);
                setChecking(false);
                return;
            }

            setAllowed(false);
            setChecking(false);
        };

        checkSession();
    }, [location.pathname]);

    if (checking) {
        return <div className="loading-screen">Checking session...</div>;
    }

    if (!allowed) {
        handleLogout();
        return <Navigate to="/login" replace />;
    }

    return <Outlet />;
};