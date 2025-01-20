package com.example.ventaService.errors;

import com.fasterxml.jackson.databind.JsonMappingException;
import feign.FeignException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.ventaService.dtos.ResponseMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase que maneja las excepciones de la aplicación
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseMessage> handleJsonParseException(HttpMessageNotReadableException e) {
        String errorMessage = "Hubo un problema al procesar el JSON. Asegúrate de que la solicitud esté correctamente formateada.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage<>("Error de parseo JSON", errorMessage, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseMessage> handleJsonParseException(JsonParseException e) {
        String errorMessage = "El JSON enviado es inválido. Verifica el formato.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage<>("JSON inválido", errorMessage, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseMessage> handleJsonMappingException(JsonMappingException e) {
        String errorMessage = "No se pudo mapear el JSON correctamente. Revisa los nombres de los campos y los valores.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage<>("Error de mapeo JSON", errorMessage, HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = "Hubo un problema al procesar la solicitud. Asegúrate de que los datos sean correctos.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage<>("Error interno", errorMessage, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ResponseMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        DateTimeFormatter formatoEspañol = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
        LocalDate fecha = LocalDate.now();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ResponseMessage<>("Método "+e.getMethod()+" no permitido", e.getMessage() , HttpStatus.METHOD_NOT_ALLOWED, fecha.format(formatoEspañol)));
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseMessage> handleFeignException(FeignException e) {
        if(e.getMessage().contains("500.0001")){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage.builder()
                            .message("Error interno")
                            .cause("Error en la conexión con el servicio externo")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build());
        } else if (e.getMessage().contains("400.0002")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage.builder()
                            .message("Usuario no encontrado")
                            .cause("No se ha encontrado el usuario")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build());
        } else if (e.getMessage().contains("400.0001")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage.builder()
                            .message("Sin Fondos")
                            .cause("El usuario no tiene fondos suficientes")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ResponseMessage.builder()
                        .message("Error interno")
                        .cause(e.getMessage())
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .date(LocalDateTime.now())
                        .build());
    }
    @ExceptionHandler(EmailNoValitFormatException.class)
    public ResponseEntity<ResponseMessage> handleEmailNoValitFormatException(EmailNoValitFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder()
                        .message("Error en la petición")
                        .cause("El email no tiene un formato válido")
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .date(LocalDateTime.now()
                        )
                        .body(e.getMessage())
                        .build());
    }

}
