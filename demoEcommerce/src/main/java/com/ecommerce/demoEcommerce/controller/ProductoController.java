package com.ecommerce.demoEcommerce.controller;

import com.ecommerce.demoEcommerce.models.Producto;
import com.ecommerce.demoEcommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    private static final String UPLOAD_DIR = "demoEcommerce/src/main/resources/static/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Producto> guardarProducto(
            @RequestParam("id") Long id,
            @RequestParam("marca") String marca,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") Double precio,
            @RequestParam("stock") Integer stock,
            @RequestParam("foto") MultipartFile foto) {
        try {
            // Crear directorio si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
    
            // Generar nombre único para el archivo
            String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            
            // Guardar el archivo
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, foto.getBytes());
    
            // Crear el producto con la URL relativa
            Producto producto = new Producto();
            producto.setBarcode(""+id);
            producto.setMarca(marca);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setFoto("/uploads/" + fileName); // URL relativa
    
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

    @DeleteMapping("/barcode")
    public ResponseEntity<Void> eliminarProducto(@RequestParam String barcode) {
        System.out.println("Intentando eliminar producto con barcode: " + barcode);

        if (productoRepository.existsBybarcode(barcode)) {
            productoRepository.deleteBybarcode(barcode);
            System.out.println("Producto eliminado exitosamente.");
            return ResponseEntity.noContent().build(); // Respuesta 204 No Content
        } else {
            System.out.println("Producto no encontrado.");
            return ResponseEntity.notFound().build(); // Respuesta 404 Not Found
        }
    }

    
}