package dev.fercho.inventstore.dto.response;


import dev.fercho.inventstore.model.enums.TipoProducto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {

    private Long id;

    private String nombre;


    private String descripcion;


    private BigDecimal precio;


    private TipoProducto tipo;

    private boolean activo;

    private Long categoriaId;
    private String categoriaNombre;
}
