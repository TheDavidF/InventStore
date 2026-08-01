package dev.fercho.inventstore.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaResponse {

    private Long id;

    private String nombre;

    private boolean activa;
}
