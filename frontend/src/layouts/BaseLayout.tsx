import { useState } from "react";
import { NavLink } from "react-router";
import { Outlet } from "react-router";
import './BaseLayout.css'


export const BaseLayout = () => {
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

    const activeStatus = ({isActive } : {isActive : boolean}) => {
        return isActive ? "active" : "";
    }
    
    return (
        <main>
            <nav className="nav-panel">
                    <div className={`nav-links ${isMobileMenuOpen ? 'active' : ''}`}>
                        <NavLink className={activeStatus} to={'/login'} end onClick={() => setIsMobileMenuOpen(false)}>Sign in</NavLink>
                        <NavLink className={activeStatus} to={'/register'} onClick={() => setIsMobileMenuOpen(false)}>Sign up</NavLink>
                        <NavLink className={activeStatus} to={'/stock'} onClick={() => setIsMobileMenuOpen(false)}>Stock</NavLink>
                        <NavLink className={activeStatus} to={'/analytics'} onClick={() => setIsMobileMenuOpen(false)}>Analytics</NavLink>
                    </div>
                </nav>


            <Outlet/>

        </main>
    )
    
}