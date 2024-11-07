// src/routes/AppRouter.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home';
import Cartelera from '../pages/Cartelera';
import Reserva from '../pages/Reserva';
import Registro from '../pages/Registro';
import Login from '../pages/Login';
import MisReservas from '../pages/MisReservas';
import Perfil from '../pages/Perfil';
import PrivateRoute from './PrivateRoute';

function AppRouter() {
    return (
        <Router>
            <Routes>
                {/* Rutas públicas */}
                <Route path="/login" element={<Login />} />
                <Route path="/registro" element={<Registro />} />

                {/* Rutas privadas */}
                <Route element={<PrivateRoute />}>
                    <Route path="/" element={<Home />} />
                    <Route path="/cartelera" element={<Cartelera />} />
                    <Route path="/reserva" element={<Reserva />} />
                    <Route path="/mis-reservas" element={<MisReservas />} />
                    <Route path="/perfil" element={<Perfil />} />
                    {/* Otras rutas privadas */}
                </Route>

                {/* Ruta por defecto */}
                <Route path="*" element={<Login />} />
            </Routes>
        </Router>
    );
}

export default AppRouter;
