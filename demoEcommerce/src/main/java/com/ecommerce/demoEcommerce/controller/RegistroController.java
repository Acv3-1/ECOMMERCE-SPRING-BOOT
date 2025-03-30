package com.ecommerce.demoEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demoEcommerce.models.Cliente;
import com.ecommerce.demoEcommerce.repository.ClienteRepository;
//import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public String registrarCliente(@RequestParam String correo, @RequestParam String password, @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Las contraseñas no coinciden.";
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setPassword(password); // Recuerda usar encriptación como BCrypt
        
        clienteRepository.save(nuevoCliente);
        return "redirect:/";
    }
}
