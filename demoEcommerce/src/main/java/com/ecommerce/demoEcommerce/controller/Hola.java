package com.ecommerce.demoEcommerce.controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;





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
    }
    @GetMapping("/productos/")
    public String productos() {
        return "productos";
    }
    @GetMapping("/reportes/")
    public String reportes() {
        return "reportes";
    }

    @GetMapping("/ecommerce/")
    public String ecommerce() {
        return "ecommerce";
    }
}
