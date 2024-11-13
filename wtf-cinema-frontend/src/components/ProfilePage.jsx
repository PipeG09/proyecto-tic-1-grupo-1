// src/components/ProfilePage.jsx
import { useState, useEffect } from 'react';

const ProfilePage = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await fetch('/api/user/profile');
                if (response.ok) {
                    const userData = await response.json();
                    setUser(userData);
                } else {
                    setError('No se pudo cargar la información del perfil');
                }
            } catch (error) {
                setError('Error al cargar el perfil');
            } finally {
                setLoading(false);
            }
        };

        fetchUserProfile();
    }, []);

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-100 flex items-center justify-center">
                <div className="text-xl text-gray-600">Cargando perfil...</div>
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
            <div className="max-w-2xl mx-auto bg-white rounded-lg shadow-md overflow-hidden">
                <div className="bg-gray-800 px-6 py-4">
                    <h1 className="text-2xl font-bold text-white">Mi Perfil</h1>
                </div>

                <div className="p-6">
                    <div className="space-y-6">
                        <div>
                            <h2 className="text-sm font-medium text-gray-500">Nombre Completo</h2>
                            <p className="mt-1 text-lg font-semibold text-gray-900">{user?.fullname}</p>
                        </div>

                        <div>
                            <h2 className="text-sm font-medium text-gray-500">Nombre de Usuario</h2>
                            <p className="mt-1 text-lg font-semibold text-gray-900">{user?.username}</p>
                        </div>

                        <div className="border-t border-gray-200 pt-4">
                            <p className="text-sm text-gray-500">
                                Miembro desde: {new Date().toLocaleDateString()} {/* Podrías agregar un campo 'createdAt' en el backend si quieres mostrar la fecha real de registro */}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;