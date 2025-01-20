package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Clase que representa la respuesta de la validacion de una venta.
 * Contiene la informacion de la venta validada, el timestamp de la respuesta, el status de la respuesta,
 * el error en caso de que haya ocurrido alguno, el mensaje de la respuesta, y la informacion adicional.
 * @see VentaValidationRequest
 */
@Data
public class VentaValidationResponse {
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("status")
    private String status;
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private List<String> message;
    @JsonProperty("info")
    private VentaValidationRequest info;
    @JsonProperty("infoadicional")
    private String infoadicional;

    public VentaValidationResponse(String s, String success, Object o, Object o1, Object o2, String infoAdicional) {
    }

    public VentaValidationResponse() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public VentaValidationRequest getInfo() {
        return info;
    }

    public void setInfo(VentaValidationRequest info) {
        this.info = info;
    }

    public String getInfoadicional() {
        return infoadicional;
    }

    public void setInfoadicional(String infoadicional) {
        this.infoadicional = infoadicional;
    }
}
