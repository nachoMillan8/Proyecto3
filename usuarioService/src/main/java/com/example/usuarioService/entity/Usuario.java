package com.example.usuarioService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa un usuario
 * <p>
 * Un objeto de la clase `Usuario` contiene todos los detalles sobre un usuario en internet,
 * como su nombre, apellido, email y fecha de nacimiento
 * </p>
 * @author Violeta, Denis, Alejandro, Nacho
 * @version 1.0
 * @date 2024
 * */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

}
