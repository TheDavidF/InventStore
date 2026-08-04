package dev.fercho.inventstore.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class DetalleOrdenResponse {

    private Long id;

    private Long productoId;

    private String productoNombre;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    private String observacion;

}
