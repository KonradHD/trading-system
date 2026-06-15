const API_URL = "http://localhost:8080/api";
const AUTH_URL = "http://localhost:8080/auth";

export { API_URL, AUTH_URL };

export const getAccessToken = () => {
    return localStorage.getItem("token");
};

export const saveAccessToken = (token: string) => {
    localStorage.setItem("token", token);
};

export const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
};

export const refreshTokenFetch = async () => {
    const response = await fetch(`${AUTH_URL}/refresh`, {
        method: "POST",
        credentials: "include",
    });

    if (!response.ok) {
        return { success: false };
    }

    const data = await response.json();

    if (!data.token) {
        return { success: false };
    }

    saveAccessToken(data.token);

    return { success: true, token: data.token };
};

export const authFetch = async (url: string, options: RequestInit = {}) => {
    const token = getAccessToken();

    let response = await fetch(url, {
        ...options,
        credentials: "include",
        headers: {
            ...(options.headers || {}),
            Authorization: token ? `Bearer ${token}` : "",
        },
    });

    if (response.status !== 401 && response.status !== 403) {
        return response;
    }

    const refreshResult = await refreshTokenFetch();

    if (!refreshResult.success) {
        handleLogout();
        return response;
    }

    response = await fetch(url, {
        ...options,
        credentials: "include",
        headers: {
            ...(options.headers || {}),
            Authorization: `Bearer ${refreshResult.token}`,
        },
    });

    return response;
};