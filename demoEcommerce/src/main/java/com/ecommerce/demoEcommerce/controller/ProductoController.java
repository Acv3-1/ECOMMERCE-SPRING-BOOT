package com.ecommerce.demoEcommerce.controller;

import com.ecommerce.demoEcommerce.models.Producto;
import com.ecommerce.demoEcommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Producto> guardarProducto(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") Double precio,
            @RequestParam("stock") Integer stock,
            @RequestParam("foto") MultipartFile foto) {
        try {
            // Guardar la imagen en el servidor
            String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, foto.getBytes());

            // Crear el producto con la URL de la imagen
            Producto producto = new Producto();
            producto.setBarcode(""+id);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setFoto("/" + UPLOAD_DIR + fileName); // URL de la imagen

            // Guardar el producto en la base de datos
            Producto nuevoProducto = productoRepository.save(producto);

            return ResponseEntity.ok(nuevoProducto);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Guardar un producto
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoRepository.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoRepository.findAll();
        return ResponseEntity.ok(productos);
    }
}