package com.example.usuarioService.repository;

import com.example.usuarioService.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar las operaciones de persistencia de la entidad Usuario
 * Extiende de JpaRepository, proporcionando métodos básicos para realizar operaciones CRUD
 * y acceso a datos de la base de datos.
 * @author denis, violeta, alejandro, nacho
 * @since 2024-12-10
 * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}
