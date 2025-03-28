package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envios")
@Getter @Setter @NoArgsConstructor
public class Envio {
    @Id
    @Column(name = "id_envio")
    private int idEnvio;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "cc_transportista")
    private Cliente transportista;

    @ManyToOne
    @JoinColumn(name = "placa_vehiculo")
    private Vehiculo vehiculo;

    @Column(name = "estado", length = 100)
    private String estado;
}
