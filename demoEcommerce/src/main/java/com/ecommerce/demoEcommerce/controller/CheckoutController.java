package com.ecommerce.demoEcommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String mostrarCheckout() {
        return "checkout"; // Nombre del archivo HTML en la carpeta templates
    }
    
    @PostMapping("/procesar-pago")
    public String procesarPago() {
        // Lógica para procesar el pago
        return "redirect:/confirmacion"; // Redirigir a una página de confirmación
    }
}
