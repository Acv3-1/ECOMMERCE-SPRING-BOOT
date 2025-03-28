package com.ecommerce.demoEcommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pagos")
@Getter @Setter @NoArgsConstructor
public class Pago {
    @Id
    @Column(name = "id_pago")
    private int idPago;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(name = "monto_pago", length = 60)
    private String montoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago")
    private EstadoPago estadoPago;

    public enum EstadoPago {
        PENDIENTE, COMPLETADO, RECHAZADO, REEMBOLSADO
    }
}