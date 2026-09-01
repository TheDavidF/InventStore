package dev.fercho.inventstore.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class AbrirCajaRequest {


    @NotNull(message = "Se requiere el usuario")
    private Long usuarioId;


    @PositiveOrZero(message = "El monto inicial debe ser mayor a 0")
    @NotNull(message = "Se requiere un monto inicial")
    @Digits(
            integer = 8,
            fraction = 2,
            message = "El monto inicial debe tener máximo 8 enteros y 2 decimales"
    )
    private BigDecimal montoInicial;
}
