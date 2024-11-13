// src/components/MovieCard.jsx
const MovieCard = ({ movie, onReserveClick }) => {
    const handleReserveClick = () => {
        console.log('MovieCard - Movie being passed:', movie);
        console.log('MovieCard - Movie ID:', movie.movieId);
        onReserveClick(movie);
    };

    return (
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
                src={`data:image/jpeg;base64,${movie.image}`}
                alt={movie.title}
                className="w-full h-64 object-cover"
                onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = '/placeholder-movie.jpg';
                }}
            />
            <div className="p-4">
                <h2 className="text-xl font-bold text-gray-900 mb-2">{movie.title}</h2>
                <p className="text-gray-600 mb-2">
                    ID: {movie.movieId} • {movie.genre} • {movie.duration} min
                </p>
                <p className="text-gray-700 mb-4">{movie.description}</p>
                <button
                    onClick={handleReserveClick}
                    className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition-colors"
                >
                    Sacar Entrada
                </button>
            </div>
        </div>
    );
};

export default MovieCard;