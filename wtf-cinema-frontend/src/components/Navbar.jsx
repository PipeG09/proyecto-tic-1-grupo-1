// src/components/Navbar.jsx
import { Link, useLocation } from 'react-router-dom';

const Navbar = ({ onLogout }) => {
    const location = useLocation();

    const isActive = (path) => {
        return location.pathname === path;
    };

    return (
        <nav className="bg-gray-800 text-white p-4">
            <div className="container mx-auto flex justify-between items-center">
                <div className="flex items-center space-x-4">
                    <Link
                        to="/home"
                        className={`text-xl font-bold ${isActive('/home') ? 'text-blue-400' : 'text-white hover:text-gray-300'}`}
                    >
                        WTF Cinema
                    </Link>
                    <Link
                        to="/movies"
                        className={`${isActive('/movies') ? 'text-blue-400' : 'text-white hover:text-gray-300'}`}
                    >
                        Cartelera
                    </Link>
                    <Link
                        to="/reservations"
                        className={`${isActive('/reservations') ? 'text-blue-400' : 'text-white hover:text-gray-300'}`}
                    >
                        Mis Reservas
                    </Link>
                    <Link
                        to="/profile"
                        className={`${isActive('/profile') ? 'text-blue-400' : 'text-white hover:text-gray-300'}`}
                    >
                        Mi Perfil
                    </Link>
                </div>
                <button
                    onClick={onLogout}
                    className="px-4 py-2 bg-red-600 rounded hover:bg-red-700 transition-colors"
                >
                    Cerrar Sesión
                </button>
            </div>
        </nav>
    );
};

export default Navbar;