package com.ecommerce.demoEcommerce.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Value("${stripe.secret-key}")
    private String stripeApiKey;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody List<Map<String, Object>> cart) {
        Stripe.apiKey = stripeApiKey;

        if (cart == null || cart.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El carrito está vacío"));
        }

        try {
            // Crear una lista de elementos de línea para Stripe Checkout
            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
            for (Map<String, Object> item : cart) {
                double precio = Double.parseDouble(item.get("precio").toString());
                int cantidad = Integer.parseInt(item.get("cantidad").toString());

                lineItems.add(
                    SessionCreateParams.LineItem.builder()
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("cop") // Moneda
                                .setUnitAmount((long) (precio * 100)) // Precio en centavos
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(item.get("nombre").toString()) // Nombre del producto
                                        .build()
                                )
                                .build()
                        )
                        .setQuantity((long) cantidad) // Cantidad
                        .build()
                );
            }

            // Crear la sesión de Stripe Checkout
            SessionCreateParams params =
                SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // Configurar métodos de pago
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(baseUrl + "/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(baseUrl + "/cancel")
                    .setShippingAddressCollection(
                        SessionCreateParams.ShippingAddressCollection.builder()
                            .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.CO) // Código correcto para Colombia
                            .build()
                    )
                    .addShippingOption(
                        SessionCreateParams.ShippingOption.builder()
                            .setShippingRate("shr_1R9boY4cKOGuLXVm7e7ULhjQ") // ID de la tarifa de envío en Stripe
                            .build()
                    )
                    .addAllLineItem(lineItems)
                    .build();

            Session session = Session.create(params);

            // Registrar logs
            System.out.println("Carrito recibido: " + cart);
            System.out.println("Sesión creada con ID: " + session.getId());

            // Devolver la URL de la sesión al cliente
            return ResponseEntity.ok(Map.of("url", session.getUrl()));

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
