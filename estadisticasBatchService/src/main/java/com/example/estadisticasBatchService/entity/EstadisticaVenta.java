package com.example.estadisticasBatchService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String nombreEvento;
    private double precio;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public LocalDate getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(LocalDate fecha) {
//        this.fecha = fecha;
//    }
//
//    public String getNombreEvento() {
//        return nombreEvento;
//    }
//
//    public void setNombreEvento(String nombreEvento) {
//        this.nombreEvento = nombreEvento;
//    }
//
//    public double getMediaPrecio() {
//        return mediaPrecio;
//    }
//
//    public void setMediaPrecio(double mediaPrecio) {
//        this.mediaPrecio = mediaPrecio;
//    }
}
