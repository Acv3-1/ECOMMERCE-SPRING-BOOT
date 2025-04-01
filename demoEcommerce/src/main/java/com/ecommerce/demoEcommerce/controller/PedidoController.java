package com.ecommerce.demoEcommerce.controller;

import com.ecommerce.demoEcommerce.models.Pago;
import com.ecommerce.demoEcommerce.models.Pedido;
import com.ecommerce.demoEcommerce.repository.PedidoRepository;
import com.ecommerce.demoEcommerce.repository.PagoRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.List;

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

    public PedidoController(PedidoRepository pedidoRepository, PagoRepository pagoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pagoRepository = pagoRepository;
    }

    @GetMapping("/guardar")
    public ResponseEntity<String> guardarPedido(@RequestParam("session_id") String sessionId) {
        Stripe.apiKey = stripeApiKey;

        try {
            // Recuperar la sesión de Stripe
            Session session = Session.retrieve(sessionId);
            // Crear un nuevo pago
            Pago pago = new Pago();
            // Crear un nuevo pedido
            Pedido pedido = new Pedido();
            pedido.setEstado("pagado");
            pago.setEstadoPago(pago.getEstadoPago().COMPLETADO); // Asignar estado de pago
            pago.setMontoPago((session.getAmountTotal() / 100.0) +""); // Convertir de centavos a la moneda original

            // Asociar el pago al pedido
            pedido.setPago(pago);

            // Guardar en la base de datos
            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Pedido y pago guardados con éxito.");
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar el pedido: " + e.getMessage());
        }
    }
}
