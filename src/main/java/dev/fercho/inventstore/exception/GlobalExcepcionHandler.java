package dev.fercho.inventstore.exception;


import dev.fercho.inventstore.dto.response.ApiErrorResponse;
import dev.fercho.inventstore.dto.response.CampoError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExcepcionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> manejarErroresValidacion(
            MethodArgumentNotValidException exception){

        List<CampoError> errores = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new CampoError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiErrorResponse respuesta = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "La solicitud contiene errores de validación",
                errores
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    @ExceptionHandler(CorreoDuplicadoException.class)
    public ResponseEntity<ApiErrorResponse> manejarCorreoDuplicado(
            CorreoDuplicadoException excepcion) {

        CampoError error = new CampoError(
                "correo",
                excepcion.getMessage()
        );

        ApiErrorResponse respuesta = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                "No fue posible crear el usuario",
                List.of(error)
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuesta);
    }
}
