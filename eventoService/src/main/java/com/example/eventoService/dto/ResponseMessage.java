package com.example.eventoService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Estructura estandarizada de las respuestas devueltas por la API.
 * Permite más legibilidad en los ResponseMessage
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-10
 * @version 1.0
 *
 * @param <T> El tipo de datos que se haya incluido en el cuerpo de la respuesta.
 * <T> admite cualquier tipo de objeto
 */
@Data
@Builder
@AllArgsConstructor

public class ResponseMessage<T> {
    private String message;
    private String cause;
    private HttpStatus status;
    private int code;
    private String date;

    private T body;

    /**
     * Constructor principal con todos los parámetros, incluido el cuerpo de la respuesta
     *
     * @param message Mensaje de la respuesta
     * @param cause Causa de la respuesta
     * @param status Estado HTTP de la respuesta
     * @param body Cuerpo como tal de la respuesta, con sus datos respectivos
     */
    public ResponseMessage(String message, String cause, HttpStatus status, T body) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.body = body;
        this.code = status.value();
    }

    /**
     * Constructor alternativo que no incluye el cuerpo de la respuesta
     *
     * @param message Mensaje de la respuesta
     * @param cause Causa de la respuesta
     * @param status Estado HTTP de la respuesta
     */
    public ResponseMessage(String message, String cause, HttpStatus status) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.code = status.value();
    }

    /**
     * Constructor alternativo sin el cuerpo, pero con un timestamp incluido
     *
     * @param message Mensaje de la respuesta
     * @param cause Causa de la respuesta
     * @param status Estado HTTP de la respuesta
     * @param date Fecha en formato String del momento de emisión del mensaje
     */
    public ResponseMessage(String message, String cause, HttpStatus status, String date) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.code = status.value();
        this.date = date;
    }
}
