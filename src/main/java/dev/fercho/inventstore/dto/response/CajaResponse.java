package dev.fercho.inventstore.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CajaResponse {

    private BigDecimal montoInicial;
    private BigDecimal montoEsperado;
    private BigDecimal montoFinal;
    private BigDecimal totalPagos;
    private BigDecimal diferencia;

}
