package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter @Setter @NoArgsConstructor
public class Pedido {
    @Id
    @Column(name = "id_pedido")
    private int idPedido;

    @ManyToOne
    @JoinColumn(name = "cc_cliente_ped")
    private Cliente cliente;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "estado", length = 200)
    private String estado;

    @Column(name = "monto_total")
    private float montoTotal;

    @OneToOne(mappedBy = "pedido")
    private Pago pago;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Envio envio;
    @OneToMany(mappedBy = "pedido")
    private List<Envio> envios = new ArrayList<>();

}
