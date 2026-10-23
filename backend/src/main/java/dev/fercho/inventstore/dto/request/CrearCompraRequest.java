package dev.fercho.inventstore.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CrearCompraRequest {

    @NotNull(message = "es necesario el id del proveedor")
    private Long proveedorId;

    @Valid
    private List<AgregarDetalleCompraRequest> detalles;

}
