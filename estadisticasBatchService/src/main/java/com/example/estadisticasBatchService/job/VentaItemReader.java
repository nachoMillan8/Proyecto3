package com.example.estadisticasBatchService.job;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import com.example.estadisticasBatchService.feignClients.VentaClient;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaItemReader implements ItemReader<List<DtoVenta>> {
    @Autowired
    private VentaClient ventaClient;

    private String fecha;

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public List<DtoVenta> read() throws Exception {
        return ventaClient.obtenerVentasPorFecha(fecha);
    }
}
