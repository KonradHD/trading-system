import { useEffect, useState } from "react";
import { Outlet, useLocation } from "react-router-dom";
// import { isTokenValid } from "../validationRules";
// import { handleLogout, refreshTokenFetch } from "../services/api";
import { useRef } from "react";


export const ProtectedRoute = () => {
    const location = useLocation();

    // const isTokenValidNow = isTokenValid();
    const [refreshFailed, setRefreshFailed] = useState(false);
    const [_, setForceUpdate] = useState(0);
    const hasFetched = useRef(false);

    // useEffect(() => {
    //     setRefreshFailed(false);

    //     if (isTokenValid()) return;

    //     if (hasFetched.current) {
    //         console.log("Odświeżanie już trwa. Czekam...");
    //         return;
    //     }
    //     hasFetched.current = true;

    //     console.log("Token wymaga odświeżenia. Blokuję...");
    //     const restore = async () => {
    //         try {

    //             const response = await refreshTokenFetch();

    //             if (!response?.success) {
    //                 console.log("Refresh nieudany");
    //                 setRefreshFailed(true);
    //             } else {
    //                 console.log("Refresh udany.");
    //                 // wymuszenie re-renderingu
    //                 setForceUpdate(prev => prev + 1);
    //             }
    //         } catch (e) {
    //             console.error(e);
    //             setRefreshFailed(true);
    //         } finally {
    //             hasFetched.current = false;
    //         }
    //     };

    //     restore();

    // }, [location.pathname]);


    // if (refreshFailed) {
    //     handleLogout();
    // }

    // // Blokada widoku
    // if (!isTokenValidNow) {
    //     return <div className="loading-screen">Odświeżanie sesji...</div>;
    // }

    return <Outlet />;
};