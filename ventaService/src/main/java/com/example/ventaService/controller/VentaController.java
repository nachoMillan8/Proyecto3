package com.example.ventaService.controller;

import com.example.ventaService.dtos.VentaRequest;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.dtos.DtoVenta;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.service.VentaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador de la API REST de ventaService
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 */
@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    /**
     * Método que realiza la venta de entradas para un evento
     * @param ventaRequest Objeto VentaRequest con los datos de la venta
     * @return ResponseEntity con un mensaje de éxito o error
     */
    @CircuitBreaker(name = "eventoCB", fallbackMethod = "fallBackVentaEntradas")
    @PostMapping("/compra")
    public ResponseEntity<?> ventaEntradas(@RequestBody VentaRequest ventaRequest){
        List<ResponseMessage> errores = ventaService.validateVenta(ventaRequest);
        if(!errores.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .body(errores)
                    .build());
        }else {
            VentaEntity venta = ventaService.ventaEntradas(
                    VentaRequest.builder()
                            .userEmail(ventaRequest.getUserEmail())
                            .eventoId(ventaRequest.getEventoId())
                            .numero(ventaRequest.getNumero())
                            .precio(ventaRequest.getPrecio())
                            .cvv(ventaRequest.getCvv())
                            .yearCaducidad(ventaRequest.getYearCaducidad())
                            .usuarioId(ventaRequest.getUsuarioId())
                            .mesCaducidad(ventaRequest.getMesCaducidad())
                            .nombreTitular(ventaRequest.getNombreTitular())
                            .build()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                    .message("Venta realizada")
                    .cause("La venta realizada el "+venta.getFechaCompra()+" para el evento "+venta.getEventoId()+" ha sido registrada correctamente")
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now())
                    .body(venta)
                    .build());
        }
    }

    /**
     * Método que devuelve las ventas realizadas por un usuario
     * @param email Email del usuario
     * @return ResponseEntity con un mensaje de éxito o error
     */
    @GetMapping("/entradas/{email}")
    public ResponseEntity<ResponseMessage> getVentasByUserEmail(@PathVariable String email){
        List<DtoVenta> ventas = ventaService.getVentasByUserEmail(email);
        if(ventas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder()
                    .message("No se han encontrado ventas")
                    .cause("No se han encontrado ventas para el usuario con email "+email)
                    .status(HttpStatus.NOT_FOUND)
                    .code(HttpStatus.NOT_FOUND.value())
                    .date(LocalDateTime.now())
                    .body(ventas)
                    .build());
        }else {
            return ResponseEntity.ok(ResponseMessage.builder()
                    .message("Ventas encontradas")
                    .cause("Se han encontrado ventas para el usuario con email "+email)
                    .status(HttpStatus.OK)
                    .code(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .body(ventas)
                    .build());
        }
    }

    /**
     * Método que se ejecuta en caso de fallo en la conexión con el servicio de eventos
     * @param ventaRequest Objeto VentaRequest con los datos de la venta
     * @param e Excepción lanzada
     * @return ResponseEntity con un mensaje de error
     */
    private ResponseEntity<?> fallBackVentaEntradas(@RequestBody VentaRequest ventaRequest, RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ResponseMessage.builder()
                .message("Error de conexión con el servicio, no se ha podido conectar con la base de datos de eventos, intentelo mas tarde")
                .cause("tas olvidau de arrancar el servicio de eventos empanao")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .date(LocalDateTime.now())
               //.body(e.getMessage())
                .build());
    }

    /**
     * Método que devuelve las ventas realizadas en una fecha concreta
     * @param fecha Fecha en formato dd-MM-yyyy
     * @return ResponseEntity con un mensaje de éxito o error
     */
    @GetMapping("/entradas/fecha/{fecha}")
    public ResponseEntity<?> getVentasByFecha(@PathVariable String fecha){
        List<DtoVenta> ventas = ventaService.getVentasByFecha(fecha);
        if (ventas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseMessage.builder()
                    .message("No se han encontrado ventas")
                    .cause("No se han encontrado ventas para la fecha proporcionada: " + fecha)
                    .status(HttpStatus.NOT_FOUND)
                    .code(HttpStatus.NOT_FOUND.value())
                    .date(LocalDateTime.now())
                    .body(ventas)
                    .build()
            );
        } else {
            return ResponseEntity.ok(
                 ResponseMessage.builder()
                    .message("Ventas encontradas")
                    .cause("Se han encontrado "+ventas.size()+" ventas para la fecha: " + fecha)
                    .status(HttpStatus.OK)
                    .code(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .body(ventas)
                    .build()
            );
        }
    }
}
