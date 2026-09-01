package dev.fercho.inventstore.dto.response;


import dev.fercho.inventstore.model.enums.EstadoOrden;
import dev.fercho.inventstore.model.enums.TipoOrden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenResponse {

    private Long id;

    private EstadoOrden estadoOrden;

    private TipoOrden tipoOrden;

    private BigDecimal total;

    private LocalDateTime fechaCreacion;

    private Long usuarioId;

    private String usuarioNombre;

    private List<DetalleOrdenResponse> detalles;


}
