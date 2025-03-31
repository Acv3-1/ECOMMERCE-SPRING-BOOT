package com.ecommerce.demoEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.demoEcommerce.models.Cliente;
import com.ecommerce.demoEcommerce.repository.ClienteRepository;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Método para registrar un cliente
    @PostMapping
    public String registrarCliente(@RequestParam int cedula, @RequestParam String nombre, @RequestParam String correo, @RequestParam String password, @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/registro?error=Passwords do not match";
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setCcCliente(cedula);
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setPassword(passwordEncoder.encode(password)); // Encripta la contraseña

        clienteRepository.save(nuevoCliente);
        return "redirect:/";
    }

    // Método para validar inicio de sesión
    @PostMapping("/login")
    public String validarInicioSesion(@RequestParam String correo, @RequestParam String password) {
        Cliente cliente = clienteRepository.findByCorreo(correo);

        if (correo == null || !passwordEncoder.matches(password, cliente.getPassword())) {
            return "redirect:/?error=Invalid credentials";
        }

        return "redirect:/ecommerce/"; // Redirige al dashboard o página principa
    }
}
