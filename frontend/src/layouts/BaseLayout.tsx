import { useState } from "react";
import { NavLink } from "react-router";
import { Outlet } from "react-router";
import UserAvatarMenu from "../components/account/UserAvatarMenu";
import './BaseLayout.css'


export const BaseLayout = () => {
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
                        <NavLink className={activeStatus} to={'/login'} end onClick={() => setIsMobileMenuOpen(false)}>Sign in</NavLink>
                        <NavLink className={activeStatus} to={'/register'} onClick={() => setIsMobileMenuOpen(false)}>Sign up</NavLink>
                    </div>


                    <UserAvatarMenu
                            isDropdownOpen={isDropdownOpen}
                            setIsDropdownOpen={setIsDropdownOpen} />

                </nav>


            <Outlet/>

        </main>
    )
    
}