// src/routes/AppRouter.js
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from '../pages/Home';
import Cartelera from '../pages/Cartelera';
import Reserva from '../pages/Reserva';
import Registro from '../pages/Registro';
import Login from '../pages/Login';

function AppRouter() {
    return (
        <Router>
            <Switch>
                <Route exact path="/" component={Home} />
                <Route path="/cartelera" component={Cartelera} />
                <Route path="/reserva" component={Reserva} />
                <Route path="/registro" component={Registro} />
                <Route path="/login" component={Login} />
            </Switch>
        </Router>
    );
}

export default AppRouter;
