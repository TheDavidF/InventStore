package dev.fercho.inventstore.model;


import dev.fercho.inventstore.model.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 125)
    private String nombre;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy ="producto",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ConsumoProducto> consumos = new ArrayList<>();


    public void agregarConsumo(ConsumoProducto consumo) {
        consumos.add(consumo);
        consumo.setProducto(this);
    }
}
