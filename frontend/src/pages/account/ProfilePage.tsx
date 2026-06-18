import { useEffect, useState } from "react";
import { API_URL } from "../../services/api";
import type { DisplayableProfile } from "../../services/Types";
import "./ProfilePage.css";
import {useAuth} from "../../services/AuthProvider.tsx";

export const ProfilePage = () => {
    const [profile, setProfile] = useState<DisplayableProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const { token } = useAuth();

    useEffect(() => {
        if (!token) return;

        fetch(`${API_URL}/profile`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            credentials: "include"
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to load profile data.");
                }
                return response.json();
            })
            .then((data) => setProfile(data))
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [token]);

    if (loading) {
        return <section className="profile-loading-state">Loading profile...</section>;
    }

    if (error) {
        return (
            <section className="profile-page">
                <h1 className="page-title">User Profile</h1>
                <div className="error-card">
                    <p>{error}</p>
                </div>
            </section>
        );
    }

    if (!profile) {
        return null;
    }

    return (
        <section className="profile-page">
            <h1 className="page-title">User Profile</h1>
            <p className="page-desc">Your personal and contact information.</p>

            <div className="profile-grid">
                <div className="data-card">
                    <h2 className="card-title">Personal Information</h2>
                    <div className="card-content">
                        <p><span>First Name:</span> <strong>{profile.name}</strong></p>
                        <p><span>Last Name:</span> <strong>{profile.surname}</strong></p>
                        <p><span>Date of Birth:</span> <strong>{profile.dateOfBirth}</strong></p>
                        <p><span>Gender:</span> <strong>{profile.gender}</strong></p>
                    </div>
                </div>

                <div className="data-card">
                    <h2 className="card-title">Contact & Address</h2>
                    <div className="card-content">
                        <p><span>Email:</span> <strong>{profile.email}</strong></p>
                        <p><span>Phone:</span> <strong>{profile.phone}</strong></p>
                        <p><span>City:</span> <strong>{profile.city}</strong></p>
                        <p><span>Address:</span> <strong>{profile.address}</strong></p>
                    </div>
                </div>
            </div>
        </section>
    );
};