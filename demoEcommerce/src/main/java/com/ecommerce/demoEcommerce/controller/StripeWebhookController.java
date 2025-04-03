package com.ecommerce.demoEcommerce.controller;

//import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stripe/webhook")
public class StripeWebhookController {

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        String endpointSecret = "whsec_your_webhook_secret"; // Obtén esto desde el Dashboard de Stripe
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();
                // Guarda los datos del pedido y del pago aquí
                System.out.println("Pago exitoso para sesión: " + session.getId());
            }

            return ResponseEntity.ok("Webhook recibido");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Webhook no válido");
        }
    }
}
