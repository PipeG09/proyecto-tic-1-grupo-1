// src/components/Navbar.js
import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

function Navbar() {
    const { usuario, cerrarSesion } = useContext(AuthContext);

    return (
        <nav>
            <Link to="/">Inicio</Link>
            <Link to="/cartelera">Cartelera</Link>
            {usuario ? (
                <>
                    <button onClick={cerrarSesion}>Cerrar Sesión</button>
                </>
            ) : (
                <>
                    <Link to="/login">Iniciar Sesión</Link>
                    <Link to="/registro">Registrarse</Link>
                </>
            )}
        </nav>
    );
}

export default Navbar;
