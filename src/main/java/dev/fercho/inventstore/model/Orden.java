package dev.fercho.inventstore.model;


import dev.fercho.inventstore.model.enums.EstadoOrden;
import dev.fercho.inventstore.model.enums.TipoOrden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoOrden estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoOrden tipoOrden;

    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    @OneToMany(mappedBy = "orden",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetalleOrden> detalles = new ArrayList<>() ;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist(){

        if(fechaCreacion == null){
            fechaCreacion = LocalDateTime.now();
        }


        if(estado == null){
            estado = EstadoOrden.CREADA;
        }

        if(total == null){
            total = BigDecimal.ZERO;
        }
    }

    public void agregarDetalle(DetalleOrden detalle) {
        detalles.add(detalle);
        detalle.setOrden(this);
    }
}
