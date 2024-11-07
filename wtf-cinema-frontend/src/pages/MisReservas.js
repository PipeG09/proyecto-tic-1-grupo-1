// src/pages/MisReservas.js
import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';

function MisReservas() {
    const [reservas, setReservas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get('/api/reservations/reservations')
            .then((response) => {
                setReservas(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error al obtener las reservas:', error);
                setLoading(false);
            });
    }, []);

    const handleCancel = (reservationId) => {
        axios
            .delete(`/api/reservations/cancel/${reservationId}`)
            .then(() => {
                setReservas(reservas.filter((reserva) => reserva.id !== reservationId));
            })
            .catch((error) => {
                console.error('Error al cancelar la reserva:', error);
            });
    };

    return (
        <div>
            <Navbar />
            <div className="container mt-4">
                <h2>Mis Reservas</h2>
                {loading ? (
                    <p>Cargando reservas...</p>
                ) : reservas.length === 0 ? (
                    <p>No tienes reservas.</p>
                ) : (
                    <ul className="list-group">
                        {reservas.map((reserva) => (
                            <li
                                className="list-group-item d-flex justify-content-between align-items-center"
                                key={reserva.id}
                            >
                                <div>
                                    <p><strong>Película:</strong> {reserva.peliculaTitulo}</p>
                                    <p><strong>Fecha y Hora:</strong> {reserva.fechaHora}</p>
                                    <p><strong>Asientos:</strong> {reserva.asientos.join(', ')}</p>
                                </div>
                                <button
                                    className="btn btn-danger"
                                    onClick={() => handleCancel(reserva.id)}
                                >
                                    Cancelar
                                </button>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}

export default MisReservas;
