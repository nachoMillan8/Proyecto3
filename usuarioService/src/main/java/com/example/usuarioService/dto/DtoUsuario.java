package com.example.usuarioService.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa la información de un usuario.
 * {@link Data}: Genera automáticamente getters, setters, y otros métodos útiles como {@code toString()}, {@code equals()}, y {@code hashCode()}
 * {@link Builder}: Permite la construcción de instancias de esta clase utilizando el patrón Builder.
 * {@link JsonFormat}: Especifica el formato de la fecha para la serialización/deserialización JSON.
 *
 * @author denis, violeta, alejandro, nacho
 * @since 2024-12-10
 * @version 1.0
 */
@Data
@Builder
public class DtoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

}
