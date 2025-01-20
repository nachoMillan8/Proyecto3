package com.example.usuarioService.errors;

/**
 * Excepcion personalizada que se lanza cuando se intenta registrar un objeto que ya existe
 * @author denis
 * @since 2024-12-10
 * @version 1.0
 */
public class DuplicadoException extends RuntimeException {
    public DuplicadoException(String mensaje) {
        super(mensaje);
    }
}
