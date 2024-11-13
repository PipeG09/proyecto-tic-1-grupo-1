// src/components/HomePage.jsx
import MovieSlider from './MovieSlider';

const HomePage = () => {
    return (
        <div className="min-h-screen bg-gray-100">
            <MovieSlider />
            <div className="container mx-auto mt-8 p-4">
                <h2 className="text-2xl font-bold mb-4">Bienvenido a WTF Cinema</h2>
                {/* Add more content here */}
            </div>
        </div>
    );
};

export default HomePage;
