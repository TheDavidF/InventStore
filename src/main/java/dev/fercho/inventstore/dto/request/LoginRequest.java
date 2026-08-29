package dev.fercho.inventstore.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "el correo es obligatorio")
    private String correo;

    @NotBlank(message = "la contraseña es obligatoria")
    private String contrasena;
}
