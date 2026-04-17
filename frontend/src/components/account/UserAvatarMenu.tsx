import { NavLink } from "react-router-dom"
import { useEffect, useRef } from "react";
import './UserAvatarMenu.css'

type UserAvatarMenuProps = {
    isDropdownOpen: boolean,
    setIsDropdownOpen: (setIsDropdownOpen: boolean) => void,
}

export default function UserAvatarMenu({ isDropdownOpen, setIsDropdownOpen }: UserAvatarMenuProps) {
    const menuRef = useRef<HTMLDivElement>(null);


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
                        u are logout
                    </div>

                    <NavLink
                        to="/login"
                        className="dropdown-item"
                        onClick={() => setIsDropdownOpen(false)}
                    >
                        Sing in
                    </NavLink>

                    <NavLink
                        to="/register"
                        className="dropdown-item"
                        onClick={() => setIsDropdownOpen(false)}
                    >
                        Sing up
                    </NavLink>
                </div>
            )}
        </div>
    )
}