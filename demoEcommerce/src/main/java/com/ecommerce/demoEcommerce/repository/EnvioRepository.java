package com.ecommerce.demoEcommerce.repository;

import com.ecommerce.demoEcommerce.models.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    List<Envio> findByEstado(String estado);  
}
