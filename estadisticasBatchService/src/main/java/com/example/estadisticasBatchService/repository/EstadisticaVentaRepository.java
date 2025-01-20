package com.example.estadisticasBatchService.repository;

import com.example.estadisticasBatchService.entity.EstadisticaVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface EstadisticaVentaRepository extends JpaRepository<EstadisticaVenta, Long> {

    boolean existsByFechaAndNombreEvento(LocalDate fecha, String nombreEvento);

}
