package com.ecommerce.demoEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.demoEcommerce.models.Cliente;
import com.ecommerce.demoEcommerce.repository.ClienteRepository;
//import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public String registrarCliente(@RequestParam int cedula, @RequestParam String nombre,@RequestParam String correo, @RequestParam String password, @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/registro?error=Passwords do not match";
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setCcCliente(cedula);
        nuevoCliente.setNombre(nombre); // Cambia esto según tu lógica
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setPassword(password); // Recuerda usar encriptación como BCrypt
        
        clienteRepository.save(nuevoCliente);
        return "redirect:/";
    }
}
