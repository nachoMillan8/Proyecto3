package com.example.estadisticasBatchService.job;

import com.example.estadisticasBatchService.entity.EstadisticaVenta;
import com.example.estadisticasBatchService.repository.EstadisticaVentaRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class EstadisticaItemWriter implements ItemWriter<Map<String, Double>> {

    @Autowired
    private EstadisticaVentaRepository estadisticaVentaRepository;

    @Override
    public void write(Chunk<? extends Map<String, Double>> items) throws Exception {
        for(Map<String, Double> map : items) {
            map.forEach((nombreEvento, mediaPrecio) -> {
                EstadisticaVenta estadistica = new EstadisticaVenta();
                estadistica.setNombreEvento(nombreEvento);
                estadistica.setPrecio(mediaPrecio);

                if (!estadisticaVentaRepository.existsByFechaAndNombreEvento(LocalDate.now(), nombreEvento)) {
                    estadisticaVentaRepository.save(estadistica);
                }
            });
        }
    }
}