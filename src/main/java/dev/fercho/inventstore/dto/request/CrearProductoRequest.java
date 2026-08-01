package dev.fercho.inventstore.dto.request;


import dev.fercho.inventstore.model.enums.TipoProducto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class CrearProductoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(max = 300)
    private String descripcion;

    @NotNull(message = "El precio es obligadorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Digits(integer=8,fraction = 2,message = "El precio debe tener solo 8 enteros y 2 decimales")
    private BigDecimal precio;

    @NotNull(message = "Debe seleccionar un tipo de producto")
    private TipoProducto tipo;

    @NotNull(message = "El producto debe pertenecer a una categoria")
    private Long categoriaId;


}
