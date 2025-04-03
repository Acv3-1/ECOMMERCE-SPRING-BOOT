package com.ecommerce.demoEcommerce.controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecommerce.demoEcommerce.repository.ProductoRepository;
import com.ecommerce.demoEcommerce.models.Cliente;
import com.ecommerce.demoEcommerce.models.Producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class Hola {
    @GetMapping("/")
    public String hola(){
        return "login";
    }
    @GetMapping("/registro/")
    public String registros() {
        return "registro";
    }
    @GetMapping("/home/")
    public String home() {
        return "home";
    }
    @GetMapping("/pedidos/")
    public String pedidos() {
        return "pedidos";
    }
    @GetMapping("/envios/")
    public String envios() {
        return "envios";
    }@Autowired
    private ProductoRepository productoRepository;
    @GetMapping("/productos/")
    public String productos(Model model) {
        List<Producto> productos = productoRepository.findAll(); // Obtiene todos los productos
        model.addAttribute("productos", productos); // Pasa los productos al modelo
        return "productos"; // Renderiza el archivo productos.html
    }
    @GetMapping("/reportes/")
    public String reportes() {
        return "reportes";
    }

    @GetMapping("/ecommerce/")
    public String ecommerce(HttpSession session, Model model) {
        // Recuperar el usuario de la sesión
        Cliente cliente = (Cliente) session.getAttribute("usuario");

        if (cliente == null) {
            return "redirect:/?error=No session found"; // Redirige si no hay sesión
        }

        // Pasar los datos del usuario al modelo
        model.addAttribute("usuario", cliente);
        model.addAttribute("productos", productoRepository.findAll()); // Obtener todos los productos y pasarlos al modelo

        return "ecommerce"; // Renderiza la vista ecommerce.html
    }
    @GetMapping("/success")
    public String success(@RequestParam("session_id") String sessionId, Model model) {
        // Aquí puedes usar el sessionId para realizar operaciones, como guardar el pedido
        model.addAttribute("sessionId", sessionId); // Pasar el sessionId al modelo si es necesario
        return "success"; // Renderiza el archivo success.html
    }
    @GetMapping("/envios")
    public String getMethodName(@RequestParam String param) {
        return "confirmacion";
    }
    
}
