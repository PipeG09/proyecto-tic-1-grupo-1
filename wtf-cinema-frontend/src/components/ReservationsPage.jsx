// src/components/ReservationsPage.jsx
import { useState, useEffect } from 'react';

const ReservationsPage = () => {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchReservations();
    }, []);

    const fetchReservations = async () => {
        try {
            const response = await fetch('/api/reservations/reservations');
            if (response.ok) {
                const data = await response.json();
                setReservations(data);
            } else {
                setError('Error al cargar las reservas');
            }
        } catch (error) {
            setError('Error al conectar con el servidor');
        } finally {
            setLoading(false);
        }
    };

    const handleCancelReservation = async (reservationId) => {
        if (!window.confirm('¿Estás seguro que deseas cancelar esta reserva?')) {
            return;
        }

        try {
            const response = await fetch(`/api/reservations/cancel/${reservationId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                // Actualizar la lista de reservas
                fetchReservations();
            } else {
                setError('Error al cancelar la reserva');
            }
        } catch (error) {
            setError('Error al conectar con el servidor');
        }
    };

    const formatSeatNumber = (row, column) => {
        return `Fila ${String.fromCharCode(65 + row)} - Asiento ${column + 1}`;
    };

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('es-UY', {
            dateStyle: 'medium',
            timeStyle: 'short'
        }).format(date);
    };

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-100 flex items-center justify-center">
                <div className="text-xl text-gray-600">Cargando reservas...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="min-h-screen bg-gray-100 flex items-center justify-center">
                <div className="text-xl text-red-600">{error}</div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-100 py-8">
            <div className="container mx-auto px-4">
                <h1 className="text-3xl font-bold text-gray-900 mb-8">Mis Reservas</h1>

                {reservations.length === 0 ? (
                    <div className="bg-white rounded-lg shadow-md p-6 text-center">
                        <p className="text-gray-600">No tienes reservas activas</p>
                    </div>
                ) : (
                    <div className="grid gap-6">
                        {reservations.map((reservation) => (
                            <div
                                key={reservation.reservationId}
                                className="bg-white rounded-lg shadow-md overflow-hidden"
                            >
                                <div className="p-6">
                                    <div className="flex justify-between items-start">
                                        <div>
                                            <h2 className="text-2xl font-bold text-gray-900 mb-2">
                                                {reservation.screening.movie.title}
                                            </h2>
                                            <div className="space-y-2 text-gray-600">
                                                <p>
                                                    <span className="font-semibold">Cine:</span>{' '}
                                                    {reservation.screening.venue.neighborhood}
                                                </p>
                                                <p>
                                                    <span className="font-semibold">Fecha y hora:</span>{' '}
                                                    {formatDate(reservation.screening.date)}
                                                </p>
                                                <p>
                                                    <span className="font-semibold">Asiento:</span>{' '}
                                                    {formatSeatNumber(reservation.seatRow, reservation.seatColumn)}
                                                </p>
                                            </div>
                                        </div>
                                        <button
                                            onClick={() => handleCancelReservation(reservation.reservationId)}
                                            className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 transition-colors"
                                        >
                                            Cancelar Reserva
                                        </button>
                                    </div>
                                </div>
                                {/* Agregar una imagen de la película si está disponible */}
                                {reservation.screening.movie.image && (
                                    <div className="border-t border-gray-200">
                                        <img
                                            src={`data:image/jpeg;base64,${reservation.screening.movie.image}`}
                                            alt={reservation.screening.movie.title}
                                            className="w-full h-48 object-cover"
                                        />
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default ReservationsPage;