import {useState} from "react";
import {NavLink, Outlet} from "react-router";
import UserAvatarMenu from "../components/account/UserAvatarMenu.tsx";

export const HomeLayout = () => {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

    const activeStatus = ({isActive } : {isActive : boolean}) => {
        return isActive ? "active" : "";
    }

    const menuIcon = () => {
        return (
            <button
                className={`hamburger ${isMobileMenuOpen ? 'active' : ''}`}
                onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
                aria-label="Toggle menu"
            >
                <span className="bar"></span>
                <span className="bar"></span>
                <span className="bar"></span>
            </button>
        );
    }

    return (
        <main>
            <nav className="nav-panel">
                {menuIcon()}

                <div className={`nav-links ${isMobileMenuOpen ? 'active' : ''}`}>
                    <NavLink className={activeStatus} to={'stock'} onClick={() => setIsMobileMenuOpen(false)}>Stock</NavLink>
                    <NavLink className={activeStatus} to={'wallets'} onClick={() => setIsMobileMenuOpen(false)}>Wallets</NavLink>
                    <NavLink className={activeStatus} to={'analytics'} onClick={() => setIsMobileMenuOpen(false)}>Analytics</NavLink>
                </div>


                <UserAvatarMenu
                    isDropdownOpen={isDropdownOpen}
                    setIsDropdownOpen={setIsDropdownOpen} />

            </nav>
            <Outlet/>

        </main>
    )

} 