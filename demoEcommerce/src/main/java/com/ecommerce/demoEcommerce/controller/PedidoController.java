package com.ecommerce.demoEcommerce.controller;

import com.ecommerce.demoEcommerce.models.Cliente;
import com.ecommerce.demoEcommerce.models.Envio;
import com.ecommerce.demoEcommerce.models.Pago;
import com.ecommerce.demoEcommerce.models.Pedido;
import com.ecommerce.demoEcommerce.models.Producto;
import com.ecommerce.demoEcommerce.repository.ClienteRepository;
import com.ecommerce.demoEcommerce.repository.EnvioRepository;
import com.ecommerce.demoEcommerce.repository.PedidoRepository;
import com.ecommerce.demoEcommerce.repository.PagoRepository;
import com.ecommerce.demoEcommerce.repository.ProductoRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Value("${stripe.secret-key}")
    private String stripeApiKey;

    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;
    private final ClienteRepository clienteRepository;
    private final EnvioRepository envioRepository;
    private final ProductoRepository productoRepository;

    public PedidoController(PedidoRepository pedidoRepository, PagoRepository pagoRepository, ClienteRepository clienteRepository, EnvioRepository envioRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pagoRepository = pagoRepository;
        this.clienteRepository = clienteRepository;
        this.envioRepository = envioRepository;
        this.productoRepository = productoRepository;
    }

    @GetMapping("/guardar")
    public ResponseEntity<String> guardarPedido(@RequestParam("session_id") String sessionId) {
        Stripe.apiKey = stripeApiKey;

        try {
            // Recuperar los productos seleccionados usando el session_id desde StripeController
            List<Map<String, Object>> productosSeleccionados = StripeController.recuperarProductosTemporales(sessionId);

            if (productosSeleccionados == null || productosSeleccionados.isEmpty()) {
                return ResponseEntity.status(400).body("No se encontraron productos para esta sesión.");
            }

            // Recuperar la sesión de Stripe
            Session session = Session.retrieve(sessionId);

            // Crear un nuevo pago
            Pago pago = new Pago();
            pago.setEstadoPago(Pago.EstadoPago.COMPLETADO); // Asignar estado de pago
            pago.setMontoPago((session.getAmountTotal() / 100.0) + ""); // Convertir de centavos a la moneda original

            // Guardar el pago primero
            pagoRepository.save(pago);

            // Crear un nuevo pedido
            Pedido pedido = new Pedido();
            pedido.setEstado("pendiente por envío"); // Estado inicial del pedido
            pedido.setPago(pago); // Asociar el pago al pedido
            pedido.setFecha(LocalDateTime.now()); // Fecha y hora actual

            // Asociar el pedido con el cliente (recuperado de la sesión de Stripe)
            String clienteCorreo = session.getCustomerDetails().getEmail(); // Obtener el correo del cliente
            Cliente cliente = clienteRepository.findByCorreo(clienteCorreo);
            if (cliente == null) {
                return ResponseEntity.status(404).body("Cliente no encontrado.");
            }
            pedido.setCliente(cliente); // Asociar el cliente al pedido

            // Guardar el pedido antes de asociar productos o envío
            pedidoRepository.save(pedido);

            // Registrar información del envío
            if (session.getShippingDetails() != null && session.getShippingDetails().getAddress() != null) {
                Envio envio = new Envio();
                envio.setDireccion(session.getShippingDetails().getAddress().getLine1()); // Dirección de envío
                envio.setCiudad(session.getShippingDetails().getAddress().getCity());
                envio.setEstado(session.getShippingDetails().getAddress().getState());
                envio.setCodigoPostal(session.getShippingDetails().getAddress().getPostalCode());
                envio.setPais(session.getShippingDetails().getAddress().getCountry());
                envio.setPedido(pedido); // Asociar el envío al pedido
                envio.setEstadoEnvio("pendiente"); // Estado inicial del envío
                envio.setEstado("pendiente"); // Estado inicial del envío

                // Guardar el envío explícitamente
                envioRepository.save(envio); // Guardar el envío explícitamente
                pedido.setEnvio(envio); // Asociar el envío al pedido
            }

            // Procesar los productos seleccionados y actualizar el stock
            for (Map<String, Object> productoSeleccionado : productosSeleccionados) {
                String productoId = productoSeleccionado.get("barcode").toString();
                int cantidadSeleccionada = (int) productoSeleccionado.get("cantidad");

                // Buscar el producto en la base de datos
                Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoId));

                // Verificar si hay suficiente stock
                if (producto.getStock() < cantidadSeleccionada) {
                    return ResponseEntity.status(400).body("Stock insuficiente para el producto: " + producto.getNombre());
                }

                // Descontar el stock
                producto.setStock(producto.getStock() - cantidadSeleccionada);

                // Asociar el producto al pedido
                pedido.getProductos().add(producto);
            }

            // Calcular el monto total del pedido
            pedido.setMontoTotal((float) (session.getAmountTotal() / 100.0)); // Convertir de centavos a la moneda original

            // Guardar el pedido actualizado
            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Pedido, pago, productos y envío guardados con éxito.");
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar el pedido: " + e.getMessage());
        }
    }
}
