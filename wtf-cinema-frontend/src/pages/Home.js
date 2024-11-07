// src/pages/Home.js
import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import axios from 'axios';
import Carousel from 'react-bootstrap/Carousel';

function Home() {
    const [peliculas, setPeliculas] = useState([]);
    const [peliculasDestacadas, setPeliculasDestacadas] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        // Obtener todas las películas
        axios
            .get('/api/movies/all')
            .then((response) => {
                const todasLasPeliculas = response.data;
                setPeliculas(todasLasPeliculas);
                // Filtrar las películas destacadas
                const destacadas = filtrarPeliculasDestacadas(todasLasPeliculas);
                setPeliculasDestacadas(destacadas);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error al obtener las películas:', error);
                setError(true);
                setLoading(false);
            });
    }, []);

    // Función para filtrar las películas destacadas
    const filtrarPeliculasDestacadas = (peliculas) => {
        // Ajusta el criterio de filtrado según tus necesidades
        // Por ejemplo, si tienes un campo 'isFeatured':
        // return peliculas.filter((pelicula) => pelicula.isFeatured);

        // O si quieres seleccionar aleatoriamente 5 películas:
        return peliculas.sort(() => 0.5 - Math.random()).slice(0, 5);

        // O seleccionar las películas con mayor rating:
        // return peliculas.sort((a, b) => b.rating - a.rating).slice(0, 5);
    };

    return (
        <div>
            <Navbar />
            <div className="container mt-4">
                {loading ? (
                    <p>Cargando películas destacadas...</p>
                ) : error ? (
                    <p>Error al cargar las películas destacadas.</p>
                ) : (
                    <Carousel>
                        {peliculasDestacadas.map((pelicula) => (
                            <Carousel.Item key={pelicula.id}>
                                <img
                                    className="d-block w-100"
                                    src={pelicula.imagenUrl}
                                    alt={pelicula.titulo}
                                />
                                <Carousel.Caption>
                                    <h3>{pelicula.titulo}</h3>
                                    <p>{pelicula.descripcion}</p>
                                </Carousel.Caption>
                            </Carousel.Item>
                        ))}
                    </Carousel>
                )}
            </div>
        </div>
    );
}

export default Home;
