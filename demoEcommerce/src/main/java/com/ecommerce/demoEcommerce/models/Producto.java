package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marca", length = 150)
    private String marca;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "foto", length = 255) // Longitud máxima de la ruta
    private String foto;
}
