package dev.fercho.inventstore.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.LongFunction;

@Getter
@Setter
@NoArgsConstructor
public class InsumoResponse {

    private Long id;

    private String nombre;

    private Integer stockActual;

    private Integer stockMinimo;

    private boolean activo;


}
