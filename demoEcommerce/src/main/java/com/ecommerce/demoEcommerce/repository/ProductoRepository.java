package com.ecommerce.demoEcommerce.repository;

import com.ecommerce.demoEcommerce.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Consulta JPQL personalizada
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN ?1 AND ?2")
    List<Producto> findByPrecioRange(double minPrecio, double maxPrecio);
    
    List<Producto> findByMarca(String marca);
    List<Producto> findByStockGreaterThan(int stock);
}