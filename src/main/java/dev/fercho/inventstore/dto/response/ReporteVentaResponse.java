package dev.fercho.inventstore.dto.response;

import dev.fercho.inventstore.model.enums.EstadoOrden;
import dev.fercho.inventstore.model.enums.TipoOrden;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReporteVentaResponse {

    private Long ordenId;
    private LocalDateTime fecha;
    private BigDecimal total;
    private TipoOrden tipoOrden;
    private EstadoOrden estado;

    private Long usuarioId;
    private String usuarioNombre;
}
