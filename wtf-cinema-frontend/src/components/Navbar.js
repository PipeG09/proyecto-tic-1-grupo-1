// src/components/Navbar.js
import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

function Navbar() {
    const navigate = useNavigate();
    const { usuario, cerrarSesion } = useContext(AuthContext);

    const handleLogout = () => {
        cerrarSesion()
            .then(() => {
                navigate('/login');
            });
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <Link className="navbar-brand" to="/">
                WTF Cinema
            </Link>
            <button
                className="navbar-toggler"
                type="button"
                data-toggle="collapse"
                data-target="#navbarNav"
                aria-controls="navbarNav"
                aria-expanded="false"
                aria-label="Alternar navegación"
            >
                <span className="navbar-toggler-icon" />
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to="/cartelera">
                            Cartelera
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/mis-reservas">
                            Mis Reservas
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/perfil">
                            Mi Perfil
                        </Link>
                    </li>
                </ul>
                <span className="navbar-text mr-3">
          Bienvenido, {usuario.username}
        </span>
                <button
                    className="btn btn-outline-danger my-2 my-sm-0"
                    onClick={handleLogout}
                >
                    Cerrar Sesión
                </button>
            </div>
        </nav>
    );
}

export default Navbar;
