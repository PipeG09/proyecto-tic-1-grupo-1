// src/components/ReservationModal.jsx
import { useState, useEffect } from 'react';

const ReservationModal = ({ isOpen, onClose, movie }) => {
    const [venues, setVenues] = useState([]);
    const [selectedVenue, setSelectedVenue] = useState(null);
    const [screenings, setScreenings] = useState([]);
    const [selectedScreening, setSelectedScreening] = useState(null);
    const [seatMatrix, setSeatMatrix] = useState(null);
    const [selectedSeat, setSelectedSeat] = useState(null);
    const [step, setStep] = useState(1);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [currentUser, setCurrentUser] = useState(null);

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
            const response = await fetch('/api/user/profile', {
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
            const response = await fetch(`/api/screenings/venues/${movie.movieId}`);
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
            const response = await fetch(`/api/screenings/screenings/${venueId}/${movie.movieId}`);
            console.log('Screenings response:', response);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
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

    const handleScreeningSelect = (screening) => {
        setSelectedScreening(screening);
        setStep(3);
        // Inicializar una matriz vacía 15x10
        const emptyMatrix = Array(15).fill().map(() => Array(10).fill(0));
        setSeatMatrix(emptyMatrix);
    };

    const handleSeatSelect = (row, col) => {
        if (seatMatrix[row][col] === 0) {
            setSelectedSeat({ row, col });
        }
    };

    const handleConfirmReservation = async () => {
        if (!selectedScreening || !selectedSeat || !currentUser) {
            setError('Falta información necesaria para la reserva');
            return;
        }

        try {
            const response = await fetch(`/api/reservations/${selectedScreening.screeningId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    screeningId: selectedScreening.screeningId,
                    userId: currentUser.id,
                    seatRow: selectedSeat.row,
                    seatColumn: selectedSeat.col
                }),
            });

            const responseText = await response.text();
            console.log('Server response:', responseText);

            if (response.ok) {
                onClose();
                alert('¡Reserva realizada con éxito!');
            } else {
                throw new Error(responseText || 'Error al crear la reserva');
            }
        } catch (error) {
            console.error('Error making reservation:', error);
            setError(error.message || 'Error al crear la reserva');
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

                        {step === 3 && seatMatrix && (
                            <div>
                                <h3 className="text-lg font-semibold mb-2">Selecciona tu asiento:</h3>
                                <div className="overflow-x-auto">
                                    <div className="grid grid-cols-10 gap-1 mb-4 w-fit mx-auto">
                                        {seatMatrix.map((row, rowIndex) => (
                                            <div key={rowIndex} className="contents">
                                                {row.map((seat, colIndex) => (
                                                    <button
                                                        key={`${rowIndex}-${colIndex}`}
                                                        className={`
                              w-8 h-8 rounded flex items-center justify-center text-xs
                              ${seat === 0
                                                            ? selectedSeat?.row === rowIndex && selectedSeat?.col === colIndex
                                                                ? 'bg-blue-500 text-white'
                                                                : 'bg-green-200 hover:bg-blue-200'
                                                            : 'bg-red-200 cursor-not-allowed'
                                                        }
                            `}
                                                        onClick={() => handleSeatSelect(rowIndex, colIndex)}
                                                        disabled={seat === 1}
                                                    >
                                                        {`${String.fromCharCode(65 + rowIndex)}${colIndex + 1}`}
                                                    </button>
                                                ))}
                                            </div>
                                        ))}
                                    </div>
                                </div>

                                {selectedSeat && (
                                    <button
                                        onClick={handleConfirmReservation}
                                        className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                                    >
                                        Confirmar Reserva
                                    </button>
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