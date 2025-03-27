document.addEventListener("DOMContentLoaded", () => {
    mostrarEnvios();
});

let envios = [
    { id: 1, pedidoId: 1001, transportista: "DHL", estado: "En preparación" },
    { id: 2, pedidoId: 1002, transportista: "FedEx", estado: "Enviado" }
];

function mostrarEnvios() {
    const tabla = document.getElementById("tabla-envios");
    tabla.innerHTML = "";
    envios.forEach((envio, index) => {
        tabla.innerHTML += `
            <tr>
                <td>${envio.id}</td>
                <td>${envio.pedidoId}</td>
                <td>${envio.transportista}</td>
                <td>${envio.estado}</td>
                <td>
                    <button class="editar" onclick="editarEnvio(${index})">Editar</button>
                    <button class="eliminar" onclick="eliminarEnvio(${index})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

function mostrarFormulario() {
    document.getElementById("formulario-envio").style.display = "block";
    document.getElementById("titulo-formulario").textContent = "Registrar Envío";
    document.getElementById("editando-id").value = "";
    document.getElementById("pedido-id").value = "";
    document.getElementById("transportista").value = "";
    document.getElementById("estado").value = "En preparación";
}

function guardarEnvio() {
    const id = document.getElementById("editando-id").value;
    const pedidoId = document.getElementById("pedido-id").value;
    const transportista = document.getElementById("transportista").value;
    const estado = document.getElementById("estado").value;

    if (pedidoId && transportista && estado) {
        if (id) {
            const index = envios.findIndex(e => e.id == id);
            envios[index] = { id: parseInt(id), pedidoId, transportista, estado };
        } else {
            const nuevoId = envios.length ? envios[envios.length - 1].id + 1 : 1;
            envios.push({ id: nuevoId, pedidoId, transportista, estado });
        }
        mostrarEnvios();
        cancelarEdicion();
    } else {
        alert("Todos los campos son obligatorios.");
    }
}

function editarEnvio(index) {
    const envio = envios[index];
    document.getElementById("formulario-envio").style.display = "block";
    document.getElementById("titulo-formulario").textContent = "Editar Envío";
    document.getElementById("editando-id").value = envio.id;
    document.getElementById("pedido-id").value = envio.pedidoId;
    document.getElementById("transportista").value = envio.transportista;
    document.getElementById("estado").value = envio.estado;
}

function eliminarEnvio(index) {
    envios.splice(index, 1);
    mostrarEnvios();
}

function cancelarEdicion() {
    document.getElementById("formulario-envio").style.display = "none";
}
