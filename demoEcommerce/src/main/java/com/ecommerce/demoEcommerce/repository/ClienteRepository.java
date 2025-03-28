package com.ecommerce.demoEcommerce.repository;
import com.ecommerce.demoEcommerce.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Consultas personalizadas
    Cliente findByCorreo(String correo);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}