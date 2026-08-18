package dev.fercho.inventstore.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "insumos")
@Getter
@Setter
@NoArgsConstructor
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,length = 100)
    private String nombre;

    @Column(nullable = false)
    private Integer stockActual;

    @Column(nullable = false)
    private Integer stockMinimo;

    @Column(nullable = false)
    private boolean activo;


    @OneToMany(mappedBy ="insumo",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ConsumoProducto> consumos = new ArrayList<>();

    public void agregarConsumo(ConsumoProducto consumo) {
        consumos.add(consumo);
        consumo.setInsumo(this);
    }

    @PrePersist
    public void prePersist(){
        activo = true;

        if(stockActual == null){
            stockActual = 0;
        }

        if(stockMinimo == null){
            stockMinimo = 0;
        }
    }

}
