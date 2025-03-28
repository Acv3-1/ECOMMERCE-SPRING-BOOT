package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;
//changes
@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @Column(name = "barcode")
    private int barcode;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "marca", length = 20)
    private String marca;

    @Column(name = "precio")
    private double precio;

    @Column(name = "stock")
    private int stock;
}
