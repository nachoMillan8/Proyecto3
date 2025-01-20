package com.example.estadisticasBatchService.service;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import com.example.estadisticasBatchService.entity.EstadisticaVenta;
import com.example.estadisticasBatchService.feignClients.VentaClient;
import com.example.estadisticasBatchService.repository.EstadisticaVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EstadisticasServiciosImpl implements EstadisticasService {
    @Autowired
    private VentaClient ventaClient;

    @Autowired
    private EstadisticaVentaRepository estadisticaVentaRepository;

    @Override
    public double calcularMediaPrecios(String fecha) {
        return ventaClient.obtenerVentasPorFecha(fecha).stream()
                .mapToDouble(DtoVenta::getPrecio)
                .average()
                .orElse(0);
    }

//    @Override
//    public void guardarEstadistica(String fecha) {
//        double media = calcularMediaPrecios(fecha);
//        EstadisticaVenta estadistica = new EstadisticaVenta();
//        estadistica.setFecha(LocalDate.parse(fecha));
//        estadistica.setMediaPrecio(media);
//        estadisticaVentaRepository.save(estadistica);
//    }
}
