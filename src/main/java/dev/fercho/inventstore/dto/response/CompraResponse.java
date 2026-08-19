package dev.fercho.inventstore.dto.response;

import dev.fercho.inventstore.model.enums.EstadoCompra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CompraResponse {


    private Long id;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaPagada;

    private BigDecimal total;

    private EstadoCompra estado;

    private List<DetalleCompraResponse> detallesCompra;

    private Long ProveedorId;

    private String nombreProveedor;

}
