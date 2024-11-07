// src/routes/PrivateRoute.js
import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';

const PrivateRoute = () => {
    const { usuario } = useContext(AuthContext);
    return usuario ? <Outlet /> : <Navigate to="/login" replace />;
};

export default PrivateRoute;
