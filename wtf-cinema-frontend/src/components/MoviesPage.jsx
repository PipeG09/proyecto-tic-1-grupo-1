// src/components/MoviesPage.jsx
import { useState, useEffect } from 'react';
import MovieCard from './MovieCard';
import ReservationModal from './ReservationModal';

const MoviesPage = () => {
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedMovie, setSelectedMovie] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const response = await fetch('/api/movies/all');
                if (response.ok) {
                    const data = await response.json();
                    setMovies(data);
                } else {
                    setError('Error al cargar las películas');
                }
            } catch (error) {
                setError('Error al conectar con el servidor');
            } finally {
                setLoading(false);
            }
        };

        fetchMovies();
    }, []);

    const handleReserveClick = (movie) => {
        console.log('Selected movie for reservation:', movie);
        setSelectedMovie(movie);
        setIsModalOpen(true);
    };

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-100 flex items-center justify-center">
                <div className="text-xl text-gray-600">Cargando películas...</div>
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
                <h1 className="text-3xl font-bold text-gray-900 mb-8">Cartelera</h1>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {movies.map((movie) => (
                        <MovieCard
                            key={movie.movieId}
                            movie={movie}
                            onReserveClick={handleReserveClick}
                        />
                    ))}
                </div>
            </div>

            {selectedMovie && (
                <ReservationModal
                    isOpen={isModalOpen}
                    onClose={() => {
                        setIsModalOpen(false);
                        setSelectedMovie(null);
                    }}
                    movie={selectedMovie}
                />
            )}
        </div>
    );
};

export default MoviesPage;