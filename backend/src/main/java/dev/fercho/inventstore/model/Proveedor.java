package dev.fercho.inventstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "proveedores")
public class Proveedor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "numero_cuenta", length = 20)
    private String numeroCuenta;

    @Column(nullable = false)
    private boolean activo = true;

    @OneToMany(mappedBy = "proveedor",
                cascade = CascadeType.ALL,
                orphanRemoval = true
    )
    private List<Compra> compras = new ArrayList<>();

}
