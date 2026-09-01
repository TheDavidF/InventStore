package dev.fercho.inventstore.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CrearConsumoProducto {


    @NotNull(message = "el producto es obligatorio")
    private Long productoId;


    @NotNull(message = "el insumo es obligatorio")
    private Long insumoId;

    @NotNull
    @Positive(message = "la cantidad debe ser mayor a 0")
    private Integer cantidad;
}
