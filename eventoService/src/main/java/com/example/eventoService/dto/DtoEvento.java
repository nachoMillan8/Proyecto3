package com.example.eventoService.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO que representa un objeto Evento. Incluye anotación @Data,
 * de Lombok, para crear automáticamente getters, setters, constructores...,
 * y permite su creación mediente el patrón Builder
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-10
 * @version 1.0
 */
@Data
@Builder
public class DtoEvento {
    Long id;

    String nombre;

    String descripcion;

    String genero;

    String localidad;

    String recinto;

    String fecha;

    double precioMin;

    double precioMax;
}
