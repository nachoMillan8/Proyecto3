package com.example.ventaService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO que representa un objeto Venta. Incluye anotación @Data,@Builder,@NoArgsConstructor y @AllArgsConstructor
 * de Lombok, para crear automáticamente getters, setters, constructores...,
 * @author Violeta, Denis, Alejandro, Nacho
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoVenta {

    private String userEmail;

    private String nombreEvento;
    private LocalDateTime fechaCompra;
    private double precio;
}
