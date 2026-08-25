package dev.fercho.inventstore.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {


    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
