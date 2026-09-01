package dev.fercho.inventstore.dto.request;


import dev.fercho.inventstore.model.enums.TipoUnidadMedida;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AgregarDetalleCompraRequest {


    @NotNull(message = "el id del insumo es obligatorio")
    private Long insumoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "la cantidad debe ser mayor a cero")
    private Integer cantidad;

    @Positive(message = "El precio unitario debe ser mayor a cero")
    @Digits(
            integer = 8,
            fraction = 2,
            message = "El precio debe tener máximo 8 enteros y 2 decimales"
    )
    private BigDecimal precioUnitario;

    @NotNull(message = "Las unidades por presentación son obligatorias")
    @Positive(message = "Las unidades por presentación deben ser mayores a cero")
    private Integer unidadesPorPresentacion;

    @Size(
            max = 300,
            message = "La observación no puede superar los 300 caracteres"
    )
    private String observacion;

    @NotNull(message = "La unidad de medida es obligatoria")
    private TipoUnidadMedida unidadMedida;


}
