package dev.fercho.inventstore.dto.response;

import dev.fercho.inventstore.model.enums.TipoUnidadMedida;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class DetalleCompraResponse {

    private Long id;

    private Long insumoId;

    private String insumoNombre;

    private Integer cantidad;

    private TipoUnidadMedida unidadMedida;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    private String observacion;

    private Integer unidadesPorPresentacion;
}
