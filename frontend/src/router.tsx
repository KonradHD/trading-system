import { createBrowserRouter } from "react-router-dom";
import { BaseLayout } from "./layouts/BaseLayout";
import { HomeLayout } from "./layouts/HomeLayout";
import { WelcomePage } from "./pages/WelcomePage";
import LoginPage from "./pages/account/LoginPage";
import RegisterPage from "./pages/account/RegisterPage";
import { HomePage } from "./pages/account/HomePage";
import { ProtectedRoute } from "./middleware/ProtectedRoute";
import { StockPage } from "./pages/stock/StockPage";
import { StockDetailsPage } from "./pages/stock/StockDetailsPage";
import { WalletDetailsPage } from "./pages/wallets/WalletDetailsPage";
import { WalletsPage } from "./pages/wallets/WalletsPage";
import { AnalyticsPage } from "./pages/analytics/AnalyticsPage";
import { ProfilePage } from "./pages/account/ProfilePage.tsx";

const router = createBrowserRouter([
    {
        path: "*",
        element: <h1>Page not found</h1>
    },
    {
        path: "/",
        element: <BaseLayout />,
        children: [
            {
                index: true,
                element: <WelcomePage />
            },
            {
                path: "stock",
                element: <StockPage />
            },
            {
                path: "stock/:symbol",
                element: <StockDetailsPage />
            },
            {
                path: "analytics",
                element: <AnalyticsPage />
            }
        ]
    },
    {
        path: "/login",
        element: <LoginPage />
    },
    {
        path: "/register",
        element: <RegisterPage />
    },
    {
        element: <ProtectedRoute />,
        children: [
            {
                path: "/home",
                element: <HomeLayout />,
                children: [
                    {
                        index: true,
                        element: <HomePage />
                    },
                    {
                        path: "stock",
                        element: <StockPage />
                    },
                    {
                        path: "stock/:symbol",
                        element: <StockDetailsPage />
                    },
                    {
                        path: "analytics",
                        element: <AnalyticsPage />
                    },
                    {
                        path: "wallets",
                        element: <WalletsPage/>
                    },
                    {
                        path: "wallets/:wallet_id",
                        element: <WalletDetailsPage />
                    },
                    {
                        path:"profile",
                        element: <ProfilePage />
                    }
                ]
            }
        ]
    }
]);

export default router;