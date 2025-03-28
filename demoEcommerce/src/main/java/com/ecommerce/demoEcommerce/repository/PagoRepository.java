package com.ecommerce.demoEcommerce.repository;

import com.ecommerce.demoEcommerce.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByEstadoPago(Pago.EstadoPago estado);
    Pago findByPedido_IdPedido(int idPedido);
}