

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
    alert("Guardando producto...");
    const id = document.getElementById("editando-id").value || null; // ID único o null si es nuevo
    const nombre = document.getElementById("nombre").value;
    const precio = document.getElementById("precio").value;
    const stock = document.getElementById("stock").value;
    const fotoInput = document.getElementById("foto");
    const marca = document.getElementById("Marca").value;
    // Validar que los campos no estén vacíos
    if (!nombre || !precio || !stock || !fotoInput.files[0]) {
        alert("Por favor, completa todos los campos y selecciona una foto.");
        return;
    }

    // Crear un objeto FormData para enviar los datos y la imagen
    const formData = new FormData();
    formData.append("id", id);
    formData.append("marca", marca);
    formData.append("nombre", nombre);
    formData.append("precio", parseFloat(precio).toFixed(2));
    formData.append("stock", parseInt(stock));
    formData.append("foto", fotoInput.files[0]); // Agregar la imagen
    // Enviar el producto al backend
    fetch("/productos/upload", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al guardar el producto.");
        }
        return response.json();
    })
    .then(data => {
        // Agregar el producto a la lista local y actualizar la tabla
        productos.push(data);
        mostrarProductos();
        cancelarEdicion();
        alert("Producto guardado exitosamente.");
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Hubo un problema al guardar el producto.");
    });
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
