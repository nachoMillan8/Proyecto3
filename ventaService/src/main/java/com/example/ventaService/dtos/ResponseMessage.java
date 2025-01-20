package com.example.ventaService.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Clase que representa la respuesta de una peticion.
 * @param <T> Tipo de dato del cuerpo de la respuesta.
 */
@Data
@Builder
@AllArgsConstructor
public class ResponseMessage<T> {
    private String message;
    private String cause;
    private HttpStatus status;
    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;

    private T body;

    public ResponseMessage(String message, String cause, HttpStatus status, T body) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.body = body;
        this.code = status.value();
    }
    public ResponseMessage(String message, String cause, HttpStatus status) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.code = status.value();
    }
    public ResponseMessage(String message, String cause, HttpStatus status, LocalDateTime date) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.code = status.value();
        this.date = date;
    }

}
