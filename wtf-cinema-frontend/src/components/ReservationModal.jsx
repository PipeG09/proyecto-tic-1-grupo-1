// src/components/ReservationModal.jsx
import { useState, useEffect } from 'react';

const ReservationModal = ({ isOpen, onClose, movie }) => {
    const [venues, setVenues] = useState([]);
    const [selectedVenue, setSelectedVenue] = useState(null);
    const [screenings, setScreenings] = useState([]);
    const [selectedScreening, setSelectedScreening] = useState(null);
    const [seatMatrix, setSeatMatrix] = useState(Array(15).fill().map(() => Array(10).fill(0)));
    const [selectedSeat, setSelectedSeat] = useState(null);
    const [step, setStep] = useState(1);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [currentUser, setCurrentUser] = useState(null);
    const [selectedSeats, setSelectedSeats] = useState([]); // Cambiamos selectedSeat por un array
    const [maxSeats] = useState(6); // Máximo de asientos por reserva

    useEffect(() => {
        if (isOpen && movie?.movieId) {
            fetchVenues();
            fetchCurrentUser();
            // Reset states when modal opens
            setSelectedVenue(null);
            setSelectedScreening(null);
            setSelectedSeat(null);
            setStep(1);
            setError(null);
        }
    }, [isOpen, movie]);

    const fetchCurrentUser = async () => {
        try {
            const response = await fetch('https://proyecto-tic-1-grupo-1.onrender.com/api/user/profile', {
                credentials: 'include'
            });
            if (response.ok) {
                const user = await response.json();
                setCurrentUser(user);
            }
        } catch (error) {
            console.error('Error fetching current user:', error);
        }
    };

    const fetchVenues = async () => {
        if (!movie?.movieId) return;

        setLoading(true);
        setError(null);
        try {
            console.log('Fetching venues for movie:', movie.movieId);
            const response = await fetch(`https://proyecto-tic-1-grupo-1.onrender.com/api/screenings/venues/${movie.movieId}`);
            console.log('Venues response:', response);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('Venues data:', data);

            if (Array.isArray(data)) {
                setVenues(data);
            } else {
                console.error('Venues data is not an array:', data);
                setVenues([]);
            }
        } catch (error) {
            console.error('Error fetching venues:', error);
            setError('Error al cargar los cines disponibles');
        } finally {
            setLoading(false);
        }
    };

    const fetchScreenings = async (venueId) => {
        setLoading(true);
        setError(null);
        try {
            console.log('Fetching screenings for venue:', venueId, 'and movie:', movie.movieId);
            const response = await fetch(`https://proyecto-tic-1-grupo-1.onrender.com/api/screenings/screenings/${venueId}/${movie.movieId}`, {
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error('Error al cargar las funciones disponibles');
            }

            const data = await response.json();
            console.log('Screenings data:', data);
            setScreenings(data);
        } catch (error) {
            console.error('Error fetching screenings:', error);
            setError('Error al cargar las funciones disponibles');
        } finally {
            setLoading(false);
        }
    };

    const handleVenueSelect = (venue) => {
        console.log('Selected venue:', venue);
        setSelectedVenue(venue);
        fetchScreenings(venue.id);
        setStep(2);
    };

    const handleScreeningSelect = async (screening) => {
        console.log('Selected screening:', screening);
        setSelectedScreening(screening);
        setStep(3);

        try {
            setLoading(true);
            const response = await fetch(`https://proyecto-tic-1-grupo-1.onrender.com/api/reservations/reservations/${screening.screeningId}`, {
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error('Error al cargar la disponibilidad de asientos');
            }

            const occupationMatrix = await response.json();
            console.log('Occupation matrix:', occupationMatrix);

            if (Array.isArray(occupationMatrix) && occupationMatrix.length === 15) {
                setSeatMatrix(occupationMatrix);
            } else {
                throw new Error('Formato de matriz inválido');
            }
        } catch (error) {
            console.error('Error fetching seat matrix:', error);
            setError('Error al cargar la disponibilidad de asientos');
            setSeatMatrix(Array(15).fill().map(() => Array(10).fill(0)));
        } finally {
            setLoading(false);
        }
    };

    const handleSeatSelect = (row, col) => {
        if (seatMatrix[row][col] === 0) { // Si el asiento está disponible
            // Buscar si el asiento ya está seleccionado
            const seatIndex = selectedSeats.findIndex(
                seat => seat.row === row && seat.col === col
            );

            if (seatIndex !== -1) {
                // Si el asiento ya está seleccionado, lo quitamos
                setSelectedSeats(prev => prev.filter((_, index) => index !== seatIndex));
            } else if (selectedSeats.length < maxSeats) {
                // Si no está seleccionado y no hemos alcanzado el máximo, lo agregamos
                setSelectedSeats(prev => [...prev, { row, col }]);
            } else {
                setError(`No puedes seleccionar más de ${maxSeats} asientos por reserva`);
            }
        }
    };

    const handleConfirmReservation = async () => {
        if (!selectedScreening || selectedSeats.length === 0 || !currentUser) {
            setError('Selecciona al menos un asiento para continuar');
            return;
        }

        try {
            // Crear un array de promesas para todas las reservas
            const reservationPromises = selectedSeats.map(seat =>
                fetch(`https://proyecto-tic-1-grupo-1.onrender.com/api/reservations/${selectedScreening.screeningId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        screeningId: selectedScreening.screeningId,
                        userId: currentUser.id,
                        seatRow: seat.row,
                        seatColumn: seat.col
                    }),
                })
            );

            // Esperar a que todas las reservas se completen
            const responses = await Promise.all(reservationPromises);

            // Verificar si todas las reservas fueron exitosas
            const allSuccessful = responses.every(response => response.ok);

            if (allSuccessful) {
                onClose();
                alert('¡Reservas realizadas con éxito!');
            } else {
                throw new Error('Error al crear algunas reservas');
            }
        } catch (error) {
            console.error('Error making reservations:', error);
            setError(error.message || 'Error al crear las reservas');
        }
    };


    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-lg p-6 max-w-lg w-full max-h-[90vh] overflow-y-auto">
                <div className="flex justify-between items-center mb-4">
                    <h2 className="text-2xl font-bold">{movie.title}</h2>
                    <button
                        onClick={onClose}
                        className="text-gray-500 hover:text-gray-700"
                    >
                        ✕
                    </button>
                </div>

                {error && (
                    <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                        {error}
                    </div>
                )}

                {loading ? (
                    <div className="text-center py-4">
                        <p>Cargando...</p>
                    </div>
                ) : (
                    <>
                        {step === 1 && (
                            <div>
                                <h3 className="text-lg font-semibold mb-2">Selecciona el cine:</h3>
                                {venues.length === 0 ? (
                                    <p className="text-gray-500">No hay cines disponibles para esta película</p>
                                ) : (
                                    <div className="space-y-2">
                                        {venues.map((venue) => (
                                            <button
                                                key={venue.id}
                                                onClick={() => handleVenueSelect(venue)}
                                                className="w-full p-2 text-left hover:bg-gray-100 rounded border"
                                            >
                                                {venue.neighborhood}
                                            </button>
                                        ))}
                                    </div>
                                )}
                            </div>
                        )}

                        {step === 2 && (
                            <div>
                                <h3 className="text-lg font-semibold mb-2">Selecciona la función:</h3>
                                {screenings.length === 0 ? (
                                    <p className="text-gray-500">No hay funciones disponibles en este cine</p>
                                ) : (
                                    <div className="space-y-2">
                                        {screenings.map((screening) => (
                                            <button
                                                key={screening.screeningId}
                                                onClick={() => handleScreeningSelect(screening)}
                                                className="w-full p-2 text-left hover:bg-gray-100 rounded border"
                                            >
                                                {new Date(screening.date).toLocaleString('es-UY', {
                                                    weekday: 'long',
                                                    year: 'numeric',
                                                    month: 'long',
                                                    day: 'numeric',
                                                    hour: '2-digit',
                                                    minute: '2-digit'
                                                })}
                                            </button>
                                        ))}
                                    </div>
                                )}
                            </div>
                        )}

                        {step === 3 && (
                            <div>
                                <h3 className="text-lg font-semibold mb-2">
                                    Selecciona tus asientos:
                                    <span className="text-sm text-gray-600 ml-2">
                    ({selectedSeats.length}/{maxSeats} seleccionados)
                  </span>
                                </h3>
                                <div className="overflow-x-auto">
                                    <div className="flex flex-col items-center space-y-1">
                                        {/* Números de columna */}
                                        <div className="flex space-x-1 mb-2 pl-6">
                                            {[...Array(10)].map((_, index) => (
                                                <div key={index} className="w-8 h-8 flex items-center justify-center text-xs text-gray-600">
                                                    {index + 1}
                                                </div>
                                            ))}
                                        </div>

                                        {/* Filas de asientos */}
                                        {seatMatrix.map((row, rowIndex) => (
                                            <div key={rowIndex} className="flex items-center">
                                                {/* Letra de la fila */}
                                                <div className="w-6 text-center text-xs text-gray-600 font-semibold mr-2">
                                                    {String.fromCharCode(65 + rowIndex)}
                                                </div>

                                                {/* Asientos */}
                                                <div className="flex space-x-1">
                                                    {row.map((isOccupied, colIndex) => (
                                                        <button
                                                            key={`${rowIndex}-${colIndex}`}
                                                            className={`
                                w-8 h-8 rounded flex items-center justify-center text-xs
                                ${isOccupied === 1
                                                                ? 'bg-red-200 cursor-not-allowed text-gray-600'
                                                                : selectedSeats.some(seat => seat.row === rowIndex && seat.col === colIndex)
                                                                    ? 'bg-blue-500 text-white'
                                                                    : 'bg-green-200 hover:bg-blue-200 text-gray-800'
                                                            }
                              `}
                                                            onClick={() => handleSeatSelect(rowIndex, colIndex)}
                                                            disabled={isOccupied === 1}
                                                            title={`Fila ${String.fromCharCode(65 + rowIndex)} - Asiento ${colIndex + 1}${
                                                                isOccupied === 1 ? ' (Ocupado)' : ''
                                                            }`}
                                                        >
                                                            {colIndex + 1}
                                                        </button>
                                                    ))}
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>

                                {/* Lista de asientos seleccionados */}
                                {selectedSeats.length > 0 && (
                                    <div className="mt-4 p-4 bg-gray-50 rounded-lg">
                                        <h4 className="font-semibold mb-2">Asientos seleccionados:</h4>
                                        <div className="flex flex-wrap gap-2">
                                            {selectedSeats.map((seat, index) => (
                                                <div
                                                    key={index}
                                                    className="flex items-center bg-blue-100 px-2 py-1 rounded"
                                                >
                          <span className="text-sm">
                            Fila {String.fromCharCode(65 + seat.row)} -
                            Asiento {seat.col + 1}
                          </span>
                                                    <button
                                                        onClick={() => handleSeatSelect(seat.row, seat.col)}
                                                        className="ml-2 text-red-500 hover:text-red-700"
                                                        title="Eliminar selección"
                                                    >
                                                        ✕
                                                    </button>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                )}

                                {/* Leyenda */}
                                <div className="mt-6 flex justify-center space-x-6">
                                    <div className="flex items-center">
                                        <div className="w-4 h-4 bg-green-200 rounded mr-2"></div>
                                        <span className="text-sm">Disponible</span>
                                    </div>
                                    <div className="flex items-center">
                                        <div className="w-4 h-4 bg-blue-500 rounded mr-2"></div>
                                        <span className="text-sm">Seleccionado</span>
                                    </div>
                                    <div className="flex items-center">
                                        <div className="w-4 h-4 bg-red-200 rounded mr-2"></div>
                                        <span className="text-sm">Ocupado</span>
                                    </div>
                                </div>

                                {/* Botón de confirmación */}
                                {selectedSeats.length > 0 && (
                                    <div className="mt-6">
                                        <button
                                            onClick={handleConfirmReservation}
                                            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition-colors"
                                        >
                                            Confirmar {selectedSeats.length} {selectedSeats.length === 1 ? 'Reserva' : 'Reservas'}
                                        </button>
                                    </div>
                                )}
                            </div>
                        )}
                    </>
                )}

                <div className="mt-4 flex justify-between">
                    <button
                        onClick={() => step > 1 ? setStep(step - 1) : onClose()}
                        className="px-4 py-2 border border-gray-300 rounded hover:bg-gray-100"
                    >
                        {step > 1 ? 'Volver' : 'Cancelar'}
                    </button>

                    <div className="flex space-x-1">
                        {[1, 2, 3].map((stepNumber) => (
                            <div
                                key={stepNumber}
                                className={`w-2 h-2 rounded-full ${
                                    step >= stepNumber ? 'bg-blue-600' : 'bg-gray-300'
                                }`}
                            />
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ReservationModal;