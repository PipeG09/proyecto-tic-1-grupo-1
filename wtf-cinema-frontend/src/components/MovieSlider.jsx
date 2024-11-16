// src/components/MovieSlider.jsx
import { useState, useEffect } from 'react';

const MovieSlider = () => {
    const [movies, setMovies] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const response = await fetch('https://proyecto-tic-1-grupo-1.onrender.com/api/movies/all');
                if (response.ok) {
                    const allMovies = await response.json();
                    // Get 5 random movies
                    const shuffled = allMovies.sort(() => 0.5 - Math.random());
                    setMovies(shuffled.slice(0, 5));
                }
            } catch (error) {
                console.error('Error fetching movies:', error);
            }
        };

        fetchMovies();
    }, []);

    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentIndex((prevIndex) =>
                prevIndex === movies.length - 1 ? 0 : prevIndex + 1
            );
        }, 5000);

        return () => clearInterval(timer);
    }, [movies.length]);

    return (
        <div className="relative h-96 overflow-hidden">
            {movies.map((movie, index) => (
                <div
                    key={index}
                    className={`absolute w-full h-full transition-opacity duration-500 ${
                        index === currentIndex ? 'opacity-100' : 'opacity-0'
                    }`}
                >
                    <img
                        src={`data:image/jpeg;base64,${movie.image}`}
                        alt={movie.title}
                        className="w-full h-full object-cover"
                        onError={(e) => {
                            e.target.onerror = null;
                            e.target.src = '/placeholder-movie.jpg'; // Puedes crear una imagen por defecto
                        }}
                    />
                    <div className="absolute bottom-0 w-full bg-black bg-opacity-50 text-white p-4">
                        <h3 className="text-xl font-bold">{movie.title}</h3>
                        <p className="text-sm">{movie.genre} • {movie.duration} min</p>
                        <p className="text-sm mt-2">{movie.description}</p>
                    </div>
                </div>
            ))}

            {/* Controles de navegación */}
            <button
                className="absolute left-0 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 hover:bg-opacity-75"
                onClick={() => setCurrentIndex(prev => prev === 0 ? movies.length - 1 : prev - 1)}
            >
                ❮
            </button>
            <button
                className="absolute right-0 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 hover:bg-opacity-75"
                onClick={() => setCurrentIndex(prev => prev === movies.length - 1 ? 0 : prev + 1)}
            >
                ❯
            </button>

            {/* Indicadores de slides */}
            <div className="absolute bottom-20 left-0 right-0 flex justify-center space-x-2">
                {movies.map((_, idx) => (
                    <button
                        key={idx}
                        className={`h-2 w-2 rounded-full ${
                            idx === currentIndex ? 'bg-white' : 'bg-white bg-opacity-50'
                        }`}
                        onClick={() => setCurrentIndex(idx)}
                    />
                ))}
            </div>
        </div>
    );
};

export default MovieSlider;