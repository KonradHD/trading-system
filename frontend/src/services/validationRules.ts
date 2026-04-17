// import type { TokenPayload } from "./components/Types";
// import { jwtDecode } from "jwt-decode";
// import type { userRole } from "./components/Types";

export const PASSWORD_RULES = [
    { id: 1, rule: (pass: string) => pass.length >= 8, text: "Minimum 8 znaków" },
    { id: 2, rule: (pass: string) => /[A-Z]/.test(pass), text: "Przynajmniej jedna duża litera" },
    { id: 3, rule: (pass: string) => /[0-9]/.test(pass), text: "Przynajmniej jedna cyfra" },
    { id: 4, rule: (pass: string) => /[!@#$%^&*(),.?":{}|<>]/.test(pass), text: "Znak specjalny" }
];


// export const isTokenValid = () => {
//     const token = localStorage.getItem('token');
//     if (!token) return false;

//     try {
//         const decoded = jwtDecode<TokenPayload>(token);
//         const currentTime = Date.now() / 1000;

//         const safetyBuffer = 5;

//         if (decoded.exp < (currentTime + safetyBuffer)) {
//             return false;
//         }

//         return true;
//     } catch (error) {
//         return false;
//     }
// };


// export const getUserFromToken = () => {
//     const token = localStorage.getItem('token');

//     if (!token) return null;

//     try {
//         if (!isTokenValid()) return null

//         return jwtDecode<TokenPayload>(token)
//     } catch (error) {
//         console.log("Error while loading user role.");
//         return null;
//     }
// }

// export const isUserLogged = () => {
//     const token = localStorage.getItem("token");
//     return token !== null;
// }


// export const getUserId = () => getUserFromToken()?.id;
// export const getUserRole = () => getUserFromToken()?.role as userRole | null;

