package com.example.estadisticasBatchService.job;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import com.example.estadisticasBatchService.entity.EstadisticaVenta;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MediaPrecioProcessor implements ItemProcessor<EstadisticaVenta, DtoVenta> {

//    @Override
//    public DtoVenta process(List<DtoVenta> ventas) {
//        return ventas.stream()
//                .collect(Collectors.groupingBy(
//                        DtoVenta::getNombreEvento,
//                        Collectors.averagingDouble(DtoVenta::getPrecio)
//                ));
//    }

    @Override
    public DtoVenta process(EstadisticaVenta estadisticaVenta) {
        return DtoVenta.builder()
                .idEvento(estadisticaVenta.getId())
                .precio(estadisticaVenta.getPrecio())
                .build();
    }
}
