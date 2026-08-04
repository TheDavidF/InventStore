package dev.fercho.inventstore.dto.request;

import dev.fercho.inventstore.model.enums.TipoOrden;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CrearOrdenRequest {

    //*
    // {
    //  "usuarioId": 1,
    //  "tipoOrden": "PARA_LLEVAR",
    //  "detalles": [
    //    {
    //      "productoId": 1,
    //      "cantidad": 2,
    //      "observacion": "Sin tomate"
    //    }
    //  ]
    //}
    //
    // *//

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "Debe seleccionar un tipo de orden")
    private TipoOrden tipoOrden;

    @Valid
    @NotEmpty(message = "La orden debe tener al menos un producto")
    private List<CrearDetalleOrdenRequest> detalles;
}
