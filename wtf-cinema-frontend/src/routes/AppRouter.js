// src/routes/AppRouter.js
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import PrivateRoute from './PrivateRoute';
import Home from '../pages/Home';
import Cartelera from '../pages/Cartelera';
import Reserva from '../pages/Reserva';
import Registro from '../pages/Registro';
import Login from '../pages/Login';
import MisReservas from '../pages/MisReservas';
import Perfil from '../pages/Perfil';

function AppRouter() {
    return (
        <Router>
            <Switch>
                {/* Rutas públicas */}
                <Route path="/login" component={Login} />
                <Route path="/registro" component={Registro} />

                {/* Rutas privadas */}
                <PrivateRoute exact path="/" component={Home} />
                <PrivateRoute path="/cartelera" component={Cartelera} />
                <PrivateRoute path="/reserva" component={Reserva} />
                <PrivateRoute path="/mis-reservas" component={MisReservas} />
                <PrivateRoute path="/perfil" component={Perfil} />
            </Switch>
        </Router>
    );
}

export default AppRouter;
