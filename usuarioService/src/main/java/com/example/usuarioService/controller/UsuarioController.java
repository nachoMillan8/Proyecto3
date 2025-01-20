package com.example.usuarioService.controller;

import java.time.LocalDateTime;
import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.dto.ResponseMessage;
import com.example.usuarioService.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-10
 * @version 1.0
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario
     * @param dtoUsuario contiene la información del usuario a registrar (nombre, apellidos, email, fecha de nacimiento)
     * @return Respuesta HTTP con el estado {@code 201 Created} si el usuario se crea correctamente
     *         El cuerpo de la respuesta contiene los datos del usuario registrado.
     */
    @PostMapping("/registro")
    ResponseEntity<?> registrarUsuario(@RequestBody DtoUsuario dtoUsuario) {
        List<ResponseMessage> errores = usuarioService.validatePost(dtoUsuario);
        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(errores)
                    .build());
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                    .message("Usuario creado")
                    .cause("El usuario "+dtoUsuario.getNombre()+" ha sido creado correctamente")
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(usuarioService.saveUsuario(dtoUsuario))
                    .build());
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody DtoUsuario dtoUsuario){
        DtoUsuario usuarioActualizado = usuarioService.updateUsuario(id, dtoUsuario);
        if (usuarioActualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder()
                    .message("Usuario no encontrado")
                    .cause("No se ha encontrado el usuario con id: " + id)
                    .status(HttpStatus.NOT_FOUND)
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder()
                .message("Usuario actualizado")
                .cause("El usuario " + dtoUsuario.getNombre() + " ha sido actualizado correctamente")
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .body(usuarioActualizado)
                .build());

    }

    /**
     * Elimina el usuario especificado si existe, encontrándolo por su ID.
     * @param id autoincremental de la entidad Usuario
     * @return ResponseEntity que contiene un ResponseMessage: si el usuario no se encuentra (HTTP 404 NOT FOUND)
     * y si se encuentra y elimina sin problemas (HTTP 200 OK)
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        DtoUsuario dtoUsuario = usuarioService.getDetalleUsuario(id);
        if (dtoUsuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseMessage.builder()
                            .message("Cliente no encontrado.")
                            .cause("Ese usuario, con #" + id + ", no está registrado.")
                            .status(HttpStatus.NOT_FOUND)
                            .code(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).build()
            );
        }

        String nombreUsuario = dtoUsuario.getNombre();
        usuarioService.deleteById(id);
        return ResponseEntity.ok(
            ResponseMessage.builder()
                    .message("Usuario eliminado correctamente.")
                    .cause("El usuario #" + id + ", con nombre " + nombreUsuario + ", fue eliminado.")
                    .status(HttpStatus.OK)
                    .code(HttpStatus.OK.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build()
        );
    }
}
