package com.example.ventaService.feignClient;

import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.dtos.VentaValidationRequest;
import com.example.ventaService.dtos.VentaValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Interface to communicate with the Banco service
 */
@FeignClient(name = "LucaBanking", url = "http://banco.eu-west-3.elasticbeanstalk.com")
public interface BancoClient {
    /**
     * Method to validate a user
     * @param user User to validate
     * @param password Password to validate
     * @return UserValidationResponse
     */
    @PostMapping(value = "/pasarela/validaruser")
    UserValidationResponse validarUsuario(@RequestParam("user") String user, @RequestParam("password") String password);

    /**
     * Method to validate a sale
     * @param request VentaValidationRequest
     * @param jwtToken JWT token
     * @return VentaValidationResponse
     */
    @PostMapping(value = "/pasarela/validacion")
    VentaValidationResponse validarVenta(@RequestBody VentaValidationRequest request, @RequestHeader("Authorization") String jwtToken);
}
