package dev.fercho.inventstore.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CampoError {


    private String campo;

    private String mensaje;

}
