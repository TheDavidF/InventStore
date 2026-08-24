package dev.fercho.inventstore.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class PagoResponse {



    private Long pagoId;
    private Long ordenId;

    private BigDecimal montoPagado;
    private BigDecimal montoRecibido;
    private BigDecimal vuelto;
    private LocalDateTime fecha;
}
