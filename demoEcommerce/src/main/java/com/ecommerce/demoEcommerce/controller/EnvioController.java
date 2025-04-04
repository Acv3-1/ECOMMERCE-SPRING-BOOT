package com.ecommerce.demoEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demoEcommerce.models.Envio;
import com.ecommerce.demoEcommerce.repository.EnvioRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {
    @Autowired
    private EnvioRepository envioRepository;

    @PostMapping("/actualizarEstado/")
    public String actualizarEstadoEnvio(@RequestBody Map<String, Object> payload) {
        Integer id = (Integer) payload.get("id");
        String estado = (String) payload.get("estado");

        // Buscar el envío en la base de datos
        Envio envio = envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Envío no encontrado"));

        // Actualizar el estado del envío
        envio.setEstadoEnvio(estado);
        envio.setEstado(estado);
        envioRepository.save(envio);

        return "Estado del envío con ID " + id + " actualizado a " + estado;
    }
}
