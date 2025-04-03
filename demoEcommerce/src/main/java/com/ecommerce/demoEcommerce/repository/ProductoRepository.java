package com.ecommerce.demoEcommerce.repository;

import com.ecommerce.demoEcommerce.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

    // Consulta JPQL personalizada para verificar si un producto existe por su ID
    @Query("SELECT COUNT(p) > 0 FROM Producto p WHERE p.id = ?1")
    boolean existsBybarcode(String barcode);

    // Consulta JPQL personalizada para eliminar un producto por su ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Producto p WHERE p.id = ?1")
    void deleteBybarcode(String barcode);

    // Consulta JPQL personalizada para buscar productos en un rango de precios
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN ?1 AND ?2")
    List<Producto> findByPrecioRange(double minPrecio, double maxPrecio);

    // Consulta JPQL personalizada para buscar productos con stock mayor a un valor
    List<Producto> findByStockGreaterThan(int stock);
}