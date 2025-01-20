package com.example.estadisticasBatchService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoVenta {
    private Long idEvento;
    private double precio;
    //    private LocalDateTime fechaCompra;

//    public String getNombreEvento() {
//        return nombreEvento;
//    }
//
//    public void setNombreEvento(String nombreEvento) {
//        this.nombreEvento = nombreEvento;
//    }
//
//    public LocalDateTime getFechaCompra() {
//        return fechaCompra;
//    }
//
//    public void setFechaCompra(LocalDateTime fechaCompra) {
//        this.fechaCompra = fechaCompra;
//    }
//
//    public double getPrecio() {
//        return precio;
//    }
//
//    public void setPrecio(double precio) {
//        this.precio = precio;
//    }
}
