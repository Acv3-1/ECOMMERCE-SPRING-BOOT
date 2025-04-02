package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envios")
@Getter @Setter @NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generar automáticamente el ID
    @Column(name = "id_envio")
    private int idEnvio;

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false) // Relación con Pedido
    private Pedido pedido;

    @Column(name = "direccion", length = 255, nullable = false)
    private String direccion;

    @Column(name = "ciudad", length = 100, nullable = false)
    private String ciudad;

    @Column(name = "estado", length = 100)
    private String estado;

    @Column(name = "codigo_postal", length = 20, nullable = false)
    private String codigoPostal;

    @Column(name = "pais", length = 100, nullable = false)
    private String pais;

    @Column(name = "estado_envio", length = 100, nullable = false)
    private String estadoEnvio; // Estado del envío (ej. "pendiente", "enviado")
}
