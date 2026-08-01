package dev.fercho.inventstore.model;

import dev.fercho.inventstore.model.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false,
    unique = true,
    length = 100)
    private String correo;


    @Column(nullable = false)
    private String contrasena;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;


    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void asignarFechaCreacion(){
        fechaCreacion = LocalDateTime.now();
    }

}
