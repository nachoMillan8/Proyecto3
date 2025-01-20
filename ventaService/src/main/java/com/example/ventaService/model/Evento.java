package com.example.ventaService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un objeto Evento.
 * Contiene la informacion de un evento, como su nombre, descripcion, genero, localidad, recinto, fecha, precio minimo y precio maximo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    //@Id
    Long id;
    String nombre;

    String descripcion;

    String genero;

    String localidad;

    String recinto;

    String fecha;

    double precioMin;

    double precioMax;

    public Evento(long l, String testEvent, double v) {
    }
}
