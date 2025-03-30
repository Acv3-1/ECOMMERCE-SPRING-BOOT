document.addEventListener("DOMContentLoaded", () => {
    mostrarProductos();
});

let productos = [
    { id: 1, nombre: "Producto A", precio: 50, stock: 20 },
    { id: 2, nombre: "Producto B", precio: 30, stock: 15 },
    { id: 3, nombre: "Storm", precio: 40, stock: 15, foto: "/uploads/storm.jpg" },
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
                <td><img src="${producto.foto}" style="max-width: 300px; max-height: 300px; object-fit: cover;"></td>
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
    document.getElementById("foto").value = "";
}

function guardarProducto() {
    const id = document.getElementById("editando-id").value || Date.now(); // Generar un ID único si no existe
    const nombre = document.getElementById("nombre").value;
    const precio = document.getElementById("precio").value;
    const stock = document.getElementById("stock").value;
    const fotoInput = document.getElementById("foto");
    const foto = fotoInput.files[0] ? URL.createObjectURL(fotoInput.files[0]) : "/uploads/default.jpg"; // Ruta predeterminada si no se selecciona una foto

    // Validar que los campos no estén vacíos
    if (!nombre || !precio || !stock) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    // Crear un nuevo objeto de producto
    const nuevoProducto = {
        id: id,
        nombre: nombre,
        precio: parseFloat(precio).toFixed(2),
        stock: parseInt(stock),
        foto: foto
    };

    // Agregar el producto a la lista de productos
    productos.push(nuevoProducto);

    // Actualizar la tabla de productos
        mostrarProductos();

    // Limpiar el formulario y ocultarlo
        cancelarEdicion();
    
    // Mostrar mensaje de éxito
        alert("Producto guardado exitosamente.");
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
