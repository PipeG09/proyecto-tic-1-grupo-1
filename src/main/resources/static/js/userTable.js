document.addEventListener("DOMContentLoaded", function() {
    // Mapa de IDs de roles a nombres de roles
    const roleIdToName = {
        2: 'ADMIN',
        1: 'USER'
        // Agrega aquí otros roles si los tienes
    };

    // Obtener el cuerpo de la tabla
    const tableBody = document.getElementById('userTableBody');

    if (!tableBody) {
        console.error('No se encontró el elemento con id "userTableBody". Asegúrate de que existe en tu HTML.');
        return;
    }

    // Hacer una llamada AJAX a la API para obtener los datos de los usuarios
    fetch('/api/users')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor: ' + response.statusText);
            }
            return response.json();  // Convertir la respuesta a JSON
        })
        .then(users => {
            // Verificar que 'users' es un array
            if (!Array.isArray(users)) {
                throw new Error('El formato de los datos recibidos no es correcto.');
            }

            // Limpiar el contenido previo de la tabla
            tableBody.innerHTML = '';

            // Iterar sobre los usuarios y crear filas en la tabla
            users.forEach(user => {
                const row = document.createElement('tr');

                // Crear las celdas
                const idCell = document.createElement('td');
                idCell.textContent = user.id !== undefined ? user.id : '';
                row.appendChild(idCell);

                const usernameCell = document.createElement('td');
                usernameCell.textContent = user.username || '';
                row.appendChild(usernameCell);

                const fullNameCell = document.createElement('td');
                fullNameCell.textContent = user.fullname || '';
                row.appendChild(fullNameCell);

                // Crear la celda de roles
                const roleCell = document.createElement('td');
                let roles = [];

                if (user.roleIds && Array.isArray(user.roleIds)) {
                    // Mapear los IDs de roles a nombres de roles
                    roles = user.roleIds.map(roleId => {
                        return roleIdToName[roleId] || 'UNKNOWN';
                    });
                }

                // Determinar si el usuario es ADMIN
                const isAdmin = roles.includes('ADMIN');

                // Mostrar 'ADMIN' o 'USER' basado en isAdmin
                roleCell.textContent = isAdmin ? 'ADMIN' : 'USER';
                row.appendChild(roleCell);

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
