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

}
