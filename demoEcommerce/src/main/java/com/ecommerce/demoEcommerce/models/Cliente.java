package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Cliente {
    @Id
    @Column(name = "cc_cliente")
    private int ccCliente;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "password", length = 20)
    private String password;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();


}