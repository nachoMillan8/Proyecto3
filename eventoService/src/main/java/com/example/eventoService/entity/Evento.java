package com.example.eventoService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa un evento con información relevante
 * <p>
 * Un objeto de la clase `Evento` contiene todos los detalles sobre un Evento específico,
 * como su  nombre, descripcion, localidad, género, recinto, fecha y precios
 * en Norteamérica, Europa, Japón y otros lugares.
 * </p>
 * @author Violeta, Denis, Alejandro, Nacho
 * @version 1.0
 * @date 2024/12/10
 * */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nombre;

    @Column(nullable = false)
    String descripcion;

    @Column(nullable = false)
    String genero;

    @Column(nullable = false)
    String localidad;

    @Column(nullable = false)
    String recinto;

    @Column(nullable = false)
    LocalDate fecha;

    @Column(nullable = false)
    double precioMin;
    
    @Column(nullable = false)
    double precioMax;


}
