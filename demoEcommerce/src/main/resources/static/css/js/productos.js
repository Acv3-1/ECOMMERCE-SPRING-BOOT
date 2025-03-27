document.addEventListener("DOMContentLoaded", () => {
    mostrarProductos();
});

let productos = [
    { id: 1, nombre: "Producto A", precio: 50, stock: 20 },
    { id: 2, nombre: "Producto B", precio: 30, stock: 15 }
];

function mostrarProductos() {
    const tabla = document.getElementById("tabla-productos");
    tabla.innerHTML = "";
    productos.forEach((producto, index) => {
        tabla.innerHTML += `
            <tr>
                <td>${producto.id}</td>
                <td>${producto.nombre}</td>
                <td>$${producto.precio}</td>
                <td>${producto.stock}</td>
                <td>
                    <button class="editar" onclick="editarProducto(${index})">Editar</button>
                    <button class="eliminar" onclick="eliminarProducto(${index})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

function mostrarFormulario() {
    document.getElementById("formulario-producto").style.display = "block";
    document.getElementById("titulo-formulario").textContent = "Agregar Producto";
    document.getElementById("editando-id").value = "";
    document.getElementById("nombre").value = "";
    document.getElementById("precio").value = "";
    document.getElementById("stock").value = "";
}

function guardarProducto() {
    const id = document.getElementById("editando-id").value;
    const nombre = document.getElementById("nombre").value;
    const precio = document.getElementById("precio").value;
    const stock = document.getElementById("stock").value;

    if (nombre && precio && stock) {
        if (id) {
            // Editar producto
            const index = productos.findIndex(p => p.id == id);
            productos[index] = { id: parseInt(id), nombre, precio: parseFloat(precio), stock: parseInt(stock) };
        } else {
            // Agregar nuevo producto
            const nuevoId = productos.length ? productos[productos.length - 1].id + 1 : 1;
            productos.push({ id: nuevoId, nombre, precio: parseFloat(precio), stock: parseInt(stock) });
        }
        mostrarProductos();
        cancelarEdicion();
    } else {
        alert("Todos los campos son obligatorios.");
    }
}

function editarProducto(index) {
    const producto = productos[index];
    document.getElementById("formulario-producto").style.display = "block";
    document.getElementById("titulo-formulario").textContent = "Editar Producto";
    document.getElementById("editando-id").value = producto.id;
    document.getElementById("nombre").value = producto.nombre;
    document.getElementById("precio").value = producto.precio;
    document.getElementById("stock").value = producto.stock;
}

function eliminarProducto(index) {
    productos.splice(index, 1);
    mostrarProductos();
}

function cancelarEdicion() {
    document.getElementById("formulario-producto").style.display = "none";
}
