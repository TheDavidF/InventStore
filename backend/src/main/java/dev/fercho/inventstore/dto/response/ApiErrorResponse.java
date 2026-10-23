package dev.fercho.inventstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class ApiErrorResponse {

    private int codigo;

    private String mensaje;

    private List<CampoError> errores;

}
