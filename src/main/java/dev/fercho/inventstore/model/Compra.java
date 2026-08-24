package dev.fercho.inventstore.model;

import dev.fercho.inventstore.model.enums.EstadoCompra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_pagada")
    private LocalDateTime fechaPagada;

    @OneToMany(mappedBy = "compra",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetalleCompra> detallesCompra = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCompra estado = EstadoCompra.PENDIENTE_PAGO;

    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }

        if (estado == null) {
            estado = EstadoCompra.PENDIENTE_PAGO;
        }
    }

    public void agregarDetalle(DetalleCompra detalle) {
        detallesCompra.add(detalle);
        detalle.setCompra(this);
    }

}
