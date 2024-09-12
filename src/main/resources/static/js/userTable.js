document.addEventListener("DOMContentLoaded", function() {
    // Obtener el cuerpo de la tabla
    const tableBody = document.getElementById('userTableBody');

    // Hacer una llamada AJAX a la API para obtener los datos de los usuarios
    fetch('/api/users')  // Asegúrate de que esta URL apunte a tu endpoint real
        .then(response => response.json())  // Convertir la respuesta a JSON
        .then(users => {
            // Iterar sobre los usuarios y crear filas en la tabla
            users.forEach(user => {
                const row = document.createElement('tr');

                // Crear las celdas
                const idCell = document.createElement('td');
                idCell.textContent = user.id;
                row.appendChild(idCell);

                const usernameCell = document.createElement('td');
                usernameCell.textContent = user.username;
                row.appendChild(usernameCell);

                const fullNameCell = document.createElement('td');
                fullNameCell.textContent = user.fullname;
                row.appendChild(fullNameCell);

                // Añadir la fila a la tabla
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error al obtener los datos de los usuarios:', error);
            // Mostrar un mensaje de error en la tabla
            const errorRow = document.createElement('tr');
            const errorCell = document.createElement('td');
            errorCell.setAttribute('colspan', 4);
            errorCell.textContent = 'Error al cargar los datos de los usuarios.';
            errorRow.appendChild(errorCell);
            tableBody.appendChild(errorRow);
        });
});
