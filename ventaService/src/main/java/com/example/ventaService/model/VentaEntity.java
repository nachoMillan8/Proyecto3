package com.example.ventaService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa un objeto Venta.
 * Contiene la informacion de una venta, como el id del usuario, el email del usuario, el id del evento, el nombre del evento, la fecha de compra y el precio.
 * @see Usuario
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private String userEmail;

    private Long eventoId;

    private String nombreEvento;

    private LocalDateTime fechaCompra;
    
    private double precio;
}
