// src/pages/Reserva.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Reserva() {
    const [funciones, setFunciones] = useState([]);
    const [asientos, setAsientos] = useState([]);
    const [seleccion, setSeleccion] = useState([]);

    useEffect(() => {
        // Obtener las funciones disponibles
        axios.get('/api/funciones').then((response) => {
            setFunciones(response.data);
        });
    }, []);

    const handleFuncionChange = (idFuncion) => {
        // Obtener los asientos de la función seleccionada
        axios.get(`/api/funciones/${idFuncion}/asientos`).then((response) => {
            setAsientos(response.data);
        });
    };

    const handleAsientoClick = (asiento) => {
        setSeleccion([...seleccion, asiento]);
    };

    const handleReserva = () => {
        // Enviar la reserva a la API
        axios.post('/api/reservas', { asientos: seleccion }).then((response) => {
            alert('Reserva realizada con éxito');
        });
    };

    return (
        <div>
            <h2>Reserva de Asientos</h2>
            <select onChange={(e) => handleFuncionChange(e.target.value)}>
                <option value="">Seleccione una función</option>
                {funciones.map((funcion) => (
                    <option key={funcion.id} value={funcion.id}>
                        {funcion.pelicula} - {funcion.horario}
                    </option>
                ))}
            </select>

            <div>
                {asientos.map((asiento) => (
                    <button
                        key={asiento.id}
                        onClick={() => handleAsientoClick(asiento)}
                        disabled={!asiento.disponible}
                    >
                        {asiento.fila}-{asiento.numero}
                    </button>
                ))}
            </div>

            <button onClick={handleReserva}>Confirmar Reserva</button>
        </div>
    );
}

export default Reserva;
