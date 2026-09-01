package dev.fercho.inventstore.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProveedorResponse {

    private Long id;

    private String nombre;

    private String telefono;

    private String numeroCuenta;

    private boolean activo;
}
