package com.example.eventoService.repository;

import com.example.eventoService.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de la entidad Evento
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    Optional<Evento> findByNombreAndGeneroAndFecha(String nombre, String genero, LocalDate fecha);
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Evento e WHERE e.nombre = ?1 AND e.genero = ?2 AND e.fecha = ?3 AND e.localidad = ?4 AND e.recinto = ?5 AND e.precioMin = ?6 AND e.precioMax = ?7 AND e.descripcion = ?8")
    boolean existEvento (String nombre, String genero, LocalDate fecha, String localidad, String recinto, double precioMin, double precioMax, String descripcion);
    @Query("SELECT e FROM Evento e WHERE e.nombre like %:nombre%")
    List<Evento> findByNombre(String nombre);

    String Nombre(String nombre);
}
