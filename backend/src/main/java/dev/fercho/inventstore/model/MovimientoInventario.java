package dev.fercho.inventstore.model;


import dev.fercho.inventstore.model.enums.TipoMovimientoInventario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_intentario")
@Getter
@Setter
@NoArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;

    @Enumerated(EnumType.STRING)
    private TipoMovimientoInventario tipo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

    @Column(length = 300)
    private String motivo;

    @Column(nullable = false)
    private Integer stockAnterior;

    @Column(nullable = false)
    private Integer stockPosterior;


    @PrePersist
    public void prePersist(){
        if(fecha == null){
            fecha = LocalDateTime.now();
        }
    }
}
