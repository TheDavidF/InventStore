package dev.fercho.inventstore.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearDetalleOrdenRequest {

    @NotNull(message = "Debe seleccionar un producto")
    private Long productoId;

    @NotNull(message = "Debe indicar la cantidad")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    @Size(max = 300,message = "la observacion no puede superar los 300 caracteres")
    private String observacion;


}
