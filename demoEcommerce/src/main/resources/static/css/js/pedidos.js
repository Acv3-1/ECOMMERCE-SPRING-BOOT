document.addEventListener("DOMContentLoaded", () => {
    mostrarPedidos();
});

let pedidos = [
    { id: 1, cliente: "Juan Pérez", fecha: "2024-03-25", estado: "Pendiente", monto: 500 },
    { id: 2, cliente: "María López", fecha: "2024-03-26", estado: "Enviado", monto: 300 }
];

function mostrarPedidos() {
    const tabla = document.getElementById("tabla-pedidos");
    tabla.innerHTML = "";
    pedidos.forEach((pedido, index) => {
        tabla.innerHTML += `
            <tr>
                <td>${pedido.id}</td>
                <td>${pedido.cliente}</td>
                <td>${pedido.fecha}</td>
                <td>${pedido.estado}</td>
                <td>$${pedido.monto}</td>
                <td>
                    <button class="editar" onclick="editarPedido(${index})">Editar</button>
                    <button class="eliminar" onclick="eliminarPedido(${index})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

function mostrarFormulario() {
    document.getElementById("formulario-pedido").style.display = "block";
    document.getElementById("titulo-formulario").textContent = "Agregar Pedido";
    document.getElementById("editando-id").value = "";
    document.getElementById("cliente").value = "";
    document.getElementById("fecha").value = "";
    document.getElementById("estado").value = "Pendiente";
    document.getElementById("monto").value = "";
}

function guardarPedido() {
    const id = document.getElementById("editando-id").value;
    const cliente = document.getElementById("cliente").value;
    const fecha = document.getElementById("fecha").value;
    const estado = document.getElementById("estado").value;
    const monto = document.getElementById("monto").value;

    if (cliente && fecha && estado && monto) {
        if (id) {
            // Editar pedido existente
            const index = pedidos.findIndex(p => p.id == id);
            pedidos[index] = { id: parseInt(id), cliente, fecha, estado, monto: parseFloat(monto) };
        } else {
            // Agregar nuevo pedido
            const nuevoId = pedidos.length ? pedidos[pedidos.length - 1].id + 1 : 1;
            pedidos.push({ id: nuevoId, cliente, fecha, estado, monto: parseFloat(monto) });
        }
        mostrarPedidos();
        cancelarEdicion();
    } else {
        alert("Todos los campos son obligatorios.");
    }
}

function editarPedido(index) {
    const pedido = pedidos[index];
    document.getElementById("formulario-pedido").style.display = "block";
    document.getElementById("titulo-formulario").textContent = "Editar Pedido";
    document.getElementById("editando-id").value = pedido.id;
    document.getElementById("cliente").value = pedido.cliente;
    document.getElementById("fecha").value = pedido.fecha;
    document.getElementById("estado").value = pedido.estado;
    document.getElementById("monto").value = pedido.monto;
}

function eliminarPedido(index) {
    pedidos.splice(index, 1);
    mostrarPedidos();
}

function cancelarEdicion() {
    document.getElementById("formulario-pedido").style.display = "none";
}
