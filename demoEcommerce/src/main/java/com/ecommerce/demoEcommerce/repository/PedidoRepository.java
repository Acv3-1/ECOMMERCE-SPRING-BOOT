package com.ecommerce.demoEcommerce.repository;

import com.ecommerce.demoEcommerce.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente_CcCliente(int ccCliente);
    
    @Query("SELECT p FROM Pedido p WHERE p.fecha BETWEEN ?1 AND ?2")
    List<Pedido> findPedidosEntreFechas(LocalDateTime inicio, LocalDateTime fin);
    
    List<Pedido> findByEstado(String estado);
}