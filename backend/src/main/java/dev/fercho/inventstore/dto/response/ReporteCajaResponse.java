package dev.fercho.inventstore.dto.response;

import dev.fercho.inventstore.model.enums.EstadoCaja;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReporteCajaResponse {

    private Long cajaId;

    private Long usuarioId;
    private String usuarioNombre;

    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;

    private BigDecimal montoInicial;
    private BigDecimal totalPagos;
    private BigDecimal montoEsperado;
    private BigDecimal montoFinal;
    private BigDecimal diferencia;

    private EstadoCaja estado;
}
