package dev.fercho.inventstore.dto.request;


import dev.fercho.inventstore.model.enums.TipoMovimientoInventario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearMovimientoInventarioRequest {


    @NotNull(message = "El insumo es obligatorio")
    private Long insumoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad no puede ser negativa")
    private Integer cantidad;

    @Size(max = 300)
    private String motivo;



}
