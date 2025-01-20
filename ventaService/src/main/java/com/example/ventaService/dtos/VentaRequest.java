package com.example.ventaService.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * Clase que representa la peticion de venta.
 * Contiene la informacion de la venta a realizar.
 */
@Data
@Builder
public class VentaRequest {
    private String nombreTitular;
    private String numero;
    private String mesCaducidad;
    private String yearCaducidad;
    private String cvv;
    private Long usuarioId;
    private Long eventoId;
    private String userEmail;
    private double precio;
}
