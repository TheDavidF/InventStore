package dev.fercho.inventstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

}
