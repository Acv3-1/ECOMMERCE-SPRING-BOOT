package com.ecommerce.demoEcommerce.controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ecommerce.demoEcommerce.repository.ProductoRepository;
import com.ecommerce.demoEcommerce.models.Producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;





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
    public String ecommerce(Model model) {
        List<Producto> productos = productoRepository.findAll(); // Obtiene todos los productos
        model.addAttribute("productos", productos); // Pasa los productos al modelo
        return "ecommerce";
    }
}
