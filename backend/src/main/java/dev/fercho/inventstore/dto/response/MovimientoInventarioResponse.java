package dev.fercho.inventstore.dto.response;


import dev.fercho.inventstore.model.enums.TipoMovimientoInventario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MovimientoInventarioResponse {

    private Long id;

    private TipoMovimientoInventario tipo;

    private LocalDateTime fecha;

    private Integer cantidad;

    private String motivo;

    private Long insumoId;

    private String nombreInsumo;
}
