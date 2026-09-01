package dev.fercho.inventstore.dto.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ActualizarProductoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(max = 300)
    private String descripcion;

    @NotNull(message = "El precio es obligadorio")
    @Positive(message = "El precio no puede ser negativo")
    @Digits(integer=8,fraction = 2,message = "El precio debe tener solo 8 enteros y 2 decimales")
    private BigDecimal precio;

}
