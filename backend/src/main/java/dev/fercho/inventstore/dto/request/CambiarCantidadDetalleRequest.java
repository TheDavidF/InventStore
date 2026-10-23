package dev.fercho.inventstore.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CambiarCantidadDetalleRequest {


    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad no puede ser negativa")
    private Integer cantidad;
}
