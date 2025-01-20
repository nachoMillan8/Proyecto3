package com.example.ventaService.feignClient;


import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Interface to communicate with the Usuario service
 */
@FeignClient(name = "usuarioService",url = "localhost:7777/usuario")
public interface UsuarioClient {
    /**
     * Method to validate a user
     * @param request UserValidationRequest
     * @return UserValidationResponse
     */
    @GetMapping("/validar")
    UserValidationResponse validarUsuario(@RequestBody UserValidationRequest request);

    /**
     * Method to register a user
     * @param dtoUsuario User to register
     * @return ResponseEntity<?>
     */
    @PostMapping("/registro")
    ResponseEntity<?> registrarUsuario(@RequestBody Usuario dtoUsuario);
}
