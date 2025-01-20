package com.example.ventaService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la peticion de venta.
 * Contiene la informacion de la venta a realizar.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidFormatForVentaRequest {
    VentaRequest ventaRequest;
    @Builder.Default
    String message = "Valid format for VentaRequest";

}
