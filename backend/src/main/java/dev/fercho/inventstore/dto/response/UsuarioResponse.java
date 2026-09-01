package dev.fercho.inventstore.dto.response;


import dev.fercho.inventstore.model.enums.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {

    private Long id;

    private String nombre;

    private String correo;

    private RolUsuario rol;

    private boolean activo;

}
