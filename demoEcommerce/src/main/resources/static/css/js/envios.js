function entregarEnvio(button) {
    // Obtener los valores de data-id y data-estado del botón
    const idEnvio = button.getAttribute('data-id');
    const estadoActual = button.getAttribute('data-estado');

    console.log("ID Envío:", idEnvio);
    console.log("Estado Actual:", estadoActual);

    // Determinar el siguiente estado basado en el estado actual
    let nuevoEstado;
    if (estadoActual === "pendiente") {
        nuevoEstado = "En proceso";
    } else if (estadoActual === "En proceso") {
        nuevoEstado = "Entregado";
    } else {
        alert("El envío ya está en el estado final.");
        return;
    }

    // Crear el cuerpo de la solicitud con el nuevo estado
    const data = {
        id: parseInt(idEnvio),
        estado: nuevoEstado
    };

    // Realizar la petición fetch al backend
    fetch(`/api/envios/actualizarEstado/`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data) // Convertir el objeto a JSON
    })
    .then(response => {
        if (response.ok) {
            alert(`El estado del envío ha sido actualizado a: ${nuevoEstado}.`);
            location.reload(); // Recargar la página para actualizar la tabla
        } else {
            return response.json().then(error => {
                throw new Error(error.message || 'Error al actualizar el estado del envío.');
            });
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un problema al procesar la solicitud.');
    });
}

