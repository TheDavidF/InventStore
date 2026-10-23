package dev.fercho.inventstore.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CrearCategoriaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max=100)
    private String nombre;

}
