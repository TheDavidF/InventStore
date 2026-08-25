package dev.fercho.inventstore.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReporteInventarioResponse {


    private Long insumoId;
    private String nombre;
    private Integer stockActual;
    private Integer stockMinimo;
    private boolean bajoStock;

}
