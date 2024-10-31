// src/pages/Cartelera.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Cartelera() {
    const [peliculas, setPeliculas] = useState([]);

    useEffect(() => {
        // Llamada a la API para obtener la lista de películas
        axios.get('/api/peliculas').then((response) => {
            setPeliculas(response.data);
        });
    }, []);

    return (
        <div>
            <h2>Cartelera</h2>
            <ul>
                {peliculas.map((pelicula) => (
                    <li key={pelicula.id}>{pelicula.titulo}</li>
                ))}
            </ul>
        </div>
    );
}

export default Cartelera;
