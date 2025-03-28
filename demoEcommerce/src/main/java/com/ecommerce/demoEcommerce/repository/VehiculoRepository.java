package com.ecommerce.demoEcommerce.repository;

import com.ecommerce.demoEcommerce.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    List<Vehiculo> findByTransportista_CcCliente(int ccTransportista);
    List<Vehiculo> findByMarca(String marca);
}
