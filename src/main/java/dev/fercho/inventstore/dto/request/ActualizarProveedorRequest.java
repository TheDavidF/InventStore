package dev.fercho.inventstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ActualizarProveedorRequest {

    @NotBlank(message = "es necesario el telefono")
    @Size(max = 15)
    private String telefono;

    @NotBlank(message = "el nombre es obligatorio")
    @Size(max = 50)
    private String nombre;

    @Size(max = 20)
    private String numeroCuenta;
}
