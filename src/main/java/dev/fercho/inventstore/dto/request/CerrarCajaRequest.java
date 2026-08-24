package dev.fercho.inventstore.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CerrarCajaRequest {


    @NotNull(message = "es requerido el monto final")
    @Positive(message = "debe ser mayor a cero")
    private BigDecimal montoFinal;
}
