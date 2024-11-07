// src/pages/Perfil.js
import React, { useEffect, useState, useContext } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

function Perfil() {
    const { usuario } = useContext(AuthContext);
    const [usuarioData, setUsuarioData] = useState(null);

    useEffect(() => {
        axios
            .get('/api/user/profile')
            .then((response) => {
                setUsuarioData(response.data);
            })
            .catch((error) => {
                console.error('Error al obtener el perfil del usuario:', error);
            });
    }, []);

    return (
        <div>
            <Navbar />
            <div className="container mt-4">
                {usuarioData ? (
                    <>
                        <h2>Mi Perfil</h2>
                        <p><strong>Nombre completo:</strong> {usuarioData.fullname}</p>
                        <p><strong>Nombre de usuario:</strong> {usuarioData.username}</p>
                        {/* Si deseas permitir la actualización de datos, puedes agregar un formulario aquí */}
                    </>
                ) : (
                    <p>Cargando perfil...</p>
                )}
            </div>
        </div>
    );
}

export default Perfil;
