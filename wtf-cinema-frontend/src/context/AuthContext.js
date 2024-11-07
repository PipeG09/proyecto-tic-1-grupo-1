// src/context/AuthContext.js
import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
    const [usuario, setUsuario] = useState(null);

    const iniciarSesion = (datosUsuario) => {
        setUsuario(datosUsuario);
    };

    const cerrarSesion = () => {
        setUsuario(null);
    };

    return (
        <AuthContext.Provider value={{ usuario, iniciarSesion, cerrarSesion }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;
