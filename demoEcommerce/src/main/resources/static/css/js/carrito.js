// Lista para almacenar los productos seleccionados en el carrito
let cart = [];

// Función para agregar un producto al carrito
function agregarAlCarrito(producto) {
  const productoExistente = cart.find(item => item.nombre === producto.nombre);

  if (productoExistente) {
    // Si ya existe, incrementar la cantidad
    productoExistente.cantidad += 1;
  } else {
    // Si no existe, agregarlo al carrito
    cart.push({ ...producto, cantidad: 1 });
  }

  // Actualizar la interfaz del carrito
  actualizarCarrito();
}

// Función para actualizar la interfaz del carrito
function actualizarCarrito() {
  const cartItems = document.getElementById("cart-items");
  const cartTotal = document.getElementById("cart-total");

  // Limpiar la lista actual
  cartItems.innerHTML = "";

  // Calcular el total
  let total = 0;

  // Agregar cada producto al carrito
  cart.forEach((producto, index) => {
    total += producto.precio * producto.cantidad;

    const li = document.createElement("li");
    li.innerHTML = `
      <div class="cart-item">
        <img src="${producto.foto}" alt="${producto.nombre}" class="cart-item-image">
        <div class="cart-item-details">
          <span class="cart-item-name">${producto.nombre}</span>
          <span class="cart-item-price">$${producto.precio.toFixed(2)}</span>
          <span class="cart-item-quantity">Cantidad: ${producto.cantidad}</span>
        </div>
        <button class="remove-item" data-index="${index}">X</button>
      </div>
    `;
    cartItems.appendChild(li);
  });

  // Actualizar el total con formato de comas
  cartTotal.textContent = `$${total.toLocaleString("es-CO")}`;

  // Guardar el carrito en localStorage
  localStorage.setItem("carrito", JSON.stringify(cart));

  // Agregar eventos para eliminar productos
  document.querySelectorAll(".remove-item").forEach(button => {
    button.addEventListener("click", (e) => {
      const index = e.target.getAttribute("data-index");
      cart.splice(index, 1); // Eliminar el producto del carrito
      actualizarCarrito(); // Actualizar la interfaz
    });
  });
}

// Función para redirigir a la pasarela de pagos
function finalizarCompra() {
  // Convertir el carrito a JSON
  const cartJSON = JSON.stringify(cart);

  // Enviar el carrito al backend
  fetch("/api/stripe/create-checkout-session", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: cartJSON, // Enviar el carrito como JSON
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.url) {
        // Redirigir al cliente a la URL de Stripe Checkout
        window.location.href = data.url;
      } else {
        console.error("Error al crear la sesión de Stripe:", data.error);
      }
    })
    .catch((error) => {
      console.error("Error en la solicitud:", error);
    });
}

document.addEventListener("DOMContentLoaded", () => {
  // Recuperar el carrito del localStorage
  const storedCart = localStorage.getItem("carrito");
  if (storedCart) {
    cart = JSON.parse(storedCart); // Convertir el JSON de vuelta a un array de objetos
    actualizarCarrito(); // Actualizar la interfaz con los datos recuperados
  }

  // Agregar eventos a los botones "Agregar al carrito"
  document.querySelectorAll(".btn.btn-small").forEach(button => {
    button.addEventListener("click", () => {
      // Obtener los datos del producto desde el DOM
      const productCard = button.closest(".product-card");
      const nombre = productCard.querySelector(".product-title").textContent;
      const precio = parseFloat(
        productCard.querySelector(".product-price").textContent.replace("$", "")
      );
      const foto = productCard.querySelector(".product-image").src;

      // Crear un objeto de producto
      const producto = {
        nombre: nombre,
        precio: precio,
        foto: foto
      };

      // Agregar el producto al carrito
      agregarAlCarrito(producto);
    });
  });
});

