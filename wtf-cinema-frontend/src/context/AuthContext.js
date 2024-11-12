// src/context/AuthContext.js
import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
    const [usuario, setUsuario] = useState(null);
    const [loading, setLoading] = useState(true);

    // Función para verificar si el usuario está autenticado al cargar la aplicación
    useEffect(() => {
        axios
            .get('/api/user/profile')
            .then((response) => {
                setUsuario(response.data);
                setLoading(false);
            })
            .catch((error) => {
                setUsuario(null);
                setLoading(false);
            });
    }, []);

    const iniciarSesion = (datosUsuario) => {
        setUsuario(datosUsuario);
    };

    const cerrarSesion = () => {
        return axios
            .get('/api/user/logout')
            .then(() => {
                setUsuario(null);
            })
            .catch((error) => {
                console.error('Error al cerrar sesión:', error);
            });
    };

    return (
        <AuthContext.Provider value={{ usuario, iniciarSesion, cerrarSesion, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;
