// src/App.js
import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import HomePage from './components/HomePage';
import ProfilePage from './components/ProfilePage';
import MoviesPage from './components/MoviesPage';
import ReservationsPage from './components/ReservationsPage';

const App = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        // Check if user is authenticated
        fetch('/api/user/profile')
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Not authenticated');
            })
            .then(() => {
                setIsAuthenticated(true);
            })
            .catch(() => {
                setIsAuthenticated(false);
            });
    }, []);

    const handleLogout = async () => {
        try {
            const response = await fetch('/api/user/logout');
            if (response.ok) {
                setIsAuthenticated(false);
                window.location.href = '/login';
            }
        } catch (error) {
            console.error('Error during logout:', error);
        }
    };

    return (
        <Router>
            <div>
                {isAuthenticated && <Navbar onLogout={handleLogout} />}
                <Routes>
                    <Route
                        path="/login"
                        element={isAuthenticated ? <Navigate to="/home" /> : <LoginPage />}
                    />
                    <Route
                        path="/register"
                        element={isAuthenticated ? <Navigate to="/home" /> : <RegisterPage />}
                    />
                    <Route
                        path="/home"
                        element={isAuthenticated ? <HomePage /> : <Navigate to="/login" />}
                    />
                    <Route
                        path="/profile"
                        element={isAuthenticated ? <ProfilePage /> : <Navigate to="/login" />}
                    />
                    <Route
                        path="/movies"
                        element={isAuthenticated ? <MoviesPage /> : <Navigate to="/login" />}
                    />
                    <Route
                        path="/reservations"
                        element={isAuthenticated ? <ReservationsPage /> : <Navigate to="/login" />}
                    />
                    <Route
                        path="/"
                        element={<Navigate to={isAuthenticated ? "/home" : "/login"} />}
                    />
                </Routes>
            </div>
        </Router>
    );
};

export default App;