package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculos")
@Getter @Setter @NoArgsConstructor
public class Vehiculo {
    @Id
    @Column(name = "placa_vehiculo", length = 6)
    private String placaVehiculo;

    @Column(name = "marca", length = 20)
    private String marca;

    @Column(name = "modelo", length = 60)
    private String modelo;

    @Column(name = "anio")
    private int anio;

    @ManyToOne
    @JoinColumn(name = "cc_transportista")
    private Cliente transportista;

    @OneToMany(mappedBy = "vehiculo")
    private List<Envio> envios = new ArrayList<>();
}