package dev.fercho.inventstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AjustarInventarioRequest {

    @NotNull
    private Long insumoId;

    @NotNull
    @PositiveOrZero
    private Integer nuevoStock;

    @NotBlank
    private String motivo;
}
