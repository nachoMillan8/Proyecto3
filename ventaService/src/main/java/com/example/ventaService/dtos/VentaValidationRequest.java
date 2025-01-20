package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la peticion de validacion de una venta.
 * Contiene la informacion de la venta a validar.
 * @see VentaValidationResponse
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "nombreTitular",
        "numeroTarjeta",
        "mesCaducidad",
        "yearCaducidad",
        "cvv",
        "emisor",
        "concepto",
        "cantidad"

})
public class VentaValidationRequest {
    @JsonProperty("numeroTarjeta")
    String numeroTarjeta;
    @JsonProperty("mesCaducidad")
    String mesCaducidad;
    @JsonProperty("yearCaducidad")
    String yearCaducidad;
    @JsonProperty("cvv")
    String cvv;
    @JsonProperty("nombreTitular")
    String nombreTitular;
    @JsonProperty("emisor")
    String emisor;
    @JsonProperty("concepto")
    String concepto;
    @JsonProperty("cantidad")
    String cantidad;


    @Override
    public String toString() {
        return "VentaValidationRequest{" +
                "numeroTarjeta='" + numeroTarjeta + '\'' +
                ", mesCaducidad='" + mesCaducidad + '\'' +
                ", yearCaducidad='" + yearCaducidad + '\'' +
                ", cvv='" + cvv + '\'' +
                ", nombreTitular='" + nombreTitular + '\'' +
                ", emisor='" + emisor + '\'' +
                ", concepto='" + concepto + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
