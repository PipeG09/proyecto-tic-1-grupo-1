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
            const response = await fetch('/api/reservations/reservations', {
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error('Error al cargar las reservas');
            }

            const data = await response.json();
            console.log('Reservations data:', data);
            setReservations(data);
        } catch (error) {
            console.error('Error fetching reservations:', error);
            setError('Error al cargar las reservas');
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
                method: 'DELETE',
                credentials: 'include'
            });

            if (response.ok) {
                fetchReservations();
            } else {
                setError('Error al cancelar la reserva');
            }
        } catch (error) {
            console.error('Error canceling reservation:', error);
            setError('Error al conectar con el servidor');
        }
    };

    const formatSeatNumber = (row, column) => {
        return `Fila ${String.fromCharCode(65 + row)} - Asiento ${column + 1}`;
    };

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('es-UY', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
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
                        {reservations.map((reservation) => {
                            if (!reservation?.screening) {
                                console.log('Reservation data:', reservation);
                                return null;
                            }

                            return (
                                <div
                                    key={reservation.reservationId}
                                    className="bg-white rounded-lg shadow-md overflow-hidden"
                                >
                                    <div className="p-6">
                                        <div className="flex flex-col md:flex-row justify-between items-start gap-4">
                                            <div className="flex-1">
                                                <h2 className="text-2xl font-bold text-gray-900 mb-4">
                                                    {reservation.screening.movie.title}
                                                </h2>
                                                <div className="space-y-3">
                                                    <div className="flex items-center">
                                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-500 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                                                        </svg>
                                                        <span className="text-gray-600">
                              {reservation.screening.venue.neighborhood}
                            </span>
                                                    </div>
                                                    <div className="flex items-center">
                                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-500 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                                                        </svg>
                                                        <span className="text-gray-600">
                              {formatDate(reservation.screening.date)}
                            </span>
                                                    </div>
                                                    <div className="flex items-center">
                                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-500 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                                                        </svg>
                                                        <span className="text-gray-600">
                              {formatSeatNumber(reservation.seatRow, reservation.seatColumn)}
                            </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <button
                                                onClick={() => handleCancelReservation(reservation.reservationId)}
                                                className="bg-red-600 text-white px-6 py-2 rounded hover:bg-red-700 transition-colors w-full md:w-auto text-center"
                                            >
                                                Cancelar Reserva
                                            </button>
                                        </div>
                                    </div>
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
                            );
                        })}
                    </div>
                )}
            </div>
        </div>
    );
};

export default ReservationsPage;