package com.ecommerce.demoEcommerce.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.ecommerce.demoEcommerce.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public boolean eliminarProducto(String barcode) {
        if (productoRepository.existsById(barcode)) {
            productoRepository.deleteById(barcode);
            return true;
        }
        return false;
    }
}
