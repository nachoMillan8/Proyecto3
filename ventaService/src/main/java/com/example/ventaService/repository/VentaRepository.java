package com.example.ventaService.repository;

import com.example.ventaService.model.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que extiende de JpaRepository para realizar operaciones con la base de datos.
 * Se encarga de gestionar las ventas.
 */
@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Long> {
    @Query("SELECT v FROM VentaEntity v WHERE v.userEmail = ?1")
    List<VentaEntity> findVentasByUserEmail(String userEmail);

    List<VentaEntity> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
}
