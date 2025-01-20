package com.example.estadisticasBatchService.feignClients;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "ventaService")
public interface VentaClient {
    @GetMapping("/venta/entradas/fecha/{fecha}")
    List<DtoVenta> obtenerVentasPorFecha(@PathVariable("fecha") String fecha);
}
