package dev.fercho.inventstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AjusteInventarioResponse {

    private Long insumoId;
    private String insumoNombre;

    private Integer stockAnterior;
    private Integer nuevoStock;
    private Integer diferencia;

    private String motivo;
}
