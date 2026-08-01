package dev.fercho.inventstore.dto.request;

import dev.fercho.inventstore.model.enums.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioRequest {

    @NotBlank(message = "El nombre es Obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene formato válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8,max = 100,message = "La contraseña debe estar entre 8 y 100 caracteres")
    private String contrasena;

    @NotNull(message = "Debe seleccionar un rol")
    private RolUsuario rol;
}
