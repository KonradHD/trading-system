import {createBrowserRouter} from "react-router-dom"
import { BaseLayout } from "./layouts/BaseLayout";
import { HomeLayout } from "./layouts/HomeLayout";
import { WelcomePage } from "./pages/WelcomePage";
import LoginPage from "./pages/account/LoginPage";
import RegisterPage from "./pages/account/RegisterPage";
import { HomePage } from "./pages/account/HomePage";
import { ProtectedRoute } from "./middleware/ProtectedRoute";


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
                element:<WelcomePage />
            }
        ]
    },

    {
        path: '/login',
        element: <LoginPage />
    },
    {
        path: '/register',
        element: <RegisterPage />,
    },

    {
        element: <ProtectedRoute />,
        children: [
            {
                path: '/home',
                element: <HomeLayout />,
                children: [
                    {
                        index: true,
                        element: <HomePage />
                    }
                ]
            }
        ]
    }
]);


export default router;