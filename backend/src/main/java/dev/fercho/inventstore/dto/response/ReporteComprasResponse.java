package dev.fercho.inventstore.dto.response;

import dev.fercho.inventstore.model.enums.EstadoCompra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class ReporteComprasResponse {


    private Long compraid;
    private LocalDateTime fechaCreacion;
    private String nombreProveedor;
    private EstadoCompra estado;

    private BigDecimal total;
    private LocalDateTime fechaPagada;
}
