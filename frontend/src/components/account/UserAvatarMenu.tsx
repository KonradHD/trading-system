import {NavLink, useNavigate} from "react-router-dom"
import { useEffect, useRef } from "react";
import './UserAvatarMenu.css'
import {handleLogout} from "../../services/api.ts";
import {useAuth} from "../../services/AuthProvider.tsx";

type UserAvatarMenuProps = {
    isDropdownOpen: boolean,
    setIsDropdownOpen: (setIsDropdownOpen: boolean) => void,
}

export default function UserAvatarMenu({ isDropdownOpen, setIsDropdownOpen }: UserAvatarMenuProps) {
    const menuRef = useRef<HTMLDivElement>(null);
    const { setAuthToken } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
                setIsDropdownOpen(false);
            }
        };
        document.addEventListener("mousedown", handleClickOutside);

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    return (
        <div className="user-menu-container" ref={menuRef}>
            <div className="account" onClick={() => setIsDropdownOpen(!isDropdownOpen)}>
                <p>My account</p>
                <img
                    src="null-avatar.png"
                    alt="Profile"
                    className="user-avatar"
                />
                    
            </div>

            {/* menu rozwijane - renderowane warunkowo */}
            {isDropdownOpen && (
                <div className="dropdown-menu">
                    <div className="dropdown-item-name" style={{ fontWeight: 'bold', borderBottom: '1px solid #eee', marginBottom: '5px' }}>
                        Logged in
                    </div>

                    <NavLink
                        to={"profile"}
                        className="dropdown-item"
                        onClick={() => setIsDropdownOpen(false)}
                    >
                        Profile
                    </NavLink>

                    <NavLink
                        to={"settings"}
                        className="dropdown-item"
                        onClick={() => setIsDropdownOpen(false)}
                    >
                        Settings
                    </NavLink>

                    <NavLink
                        to="/login"
                        className="dropdown-item"
                        onClick={() => {
                            setIsDropdownOpen(false);
                            handleLogout(setAuthToken, navigate);
                        }}
                    >
                        Logout
                    </NavLink>
                </div>
            )}
        </div>
    )
}