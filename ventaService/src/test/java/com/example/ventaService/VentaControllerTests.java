package com.example.ventaService;

import com.example.ventaService.controller.VentaController;
import com.example.ventaService.dtos.VentaRequest;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clase que contiene los tests de la clase VentaController.
 * Se encarga de realizar las pruebas unitarias de los métodos de la clase VentaController.
 * Se utilizan mocks para simular el comportamiento de la clase VentaService.
 * Se utilizan anotaciones de JUnit 5 para la ejecución de los tests.
 * Se utilizan anotaciones de Mockito para la creación de mocks.
 */
@WebMvcTest(VentaController.class)
public class VentaControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private VentaService ventaService;

        /**
         * Test que comprueba que el método ventaEntradas de la clase VentaController devuelve un BadRequest cuando la validación falla.
         * @throws Exception Excepción lanzada por el método perform.
         */
        @Test
        void ventaEntradasShouldReturnBadRequestWhenValidationFails() throws Exception {
                VentaRequest invalidRequest = VentaRequest.builder()
                        .userEmail("invalid-email")
                        .build();

                Mockito.when(ventaService.validateVenta(any(VentaRequest.class)))
                        .thenReturn(Collections.singletonList(ResponseMessage.builder().message("Invalid email").build()));

                mockMvc.perform(post("/venta/compra")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                        .andDo((result) -> System.out.println(result.getResponse().getContentAsString()))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("Error en la petición"));
        }

        /**
         * Test que comprueba que el método ventaEntradas de la clase VentaController devuelve un Created cuando la petición es válida.
         * @throws Exception Excepción lanzada por el método perform.
         */
        @Test
        void ventaEntradasShouldReturnCreatedWhenRequestIsValid() throws Exception {
                VentaRequest validRequest = VentaRequest.builder()
                        .userEmail("example@gmail.com")
                        .eventoId(1L)
                        .numero("1234")
                        .precio(50.0)
                        .cvv("123")
                        .mesCaducidad("12")
                        .yearCaducidad("2025")
                        .usuarioId(1L)
                        .nombreTitular("John Doe")
                        .build();

                VentaEntity mockVenta = new VentaEntity();
                mockVenta.setFechaCompra(LocalDateTime.now());
                mockVenta.setEventoId(1L);
                mockVenta.setUsuarioId(1L);

                Mockito.when(ventaService.validateVenta(any(VentaRequest.class)))
                        .thenReturn(Collections.emptyList());

                Mockito.when(ventaService.ventaEntradas(any(VentaRequest.class)))
                        .thenReturn(mockVenta);

                mockMvc.perform(post("/venta/compra")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validRequest)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.message").value("Venta realizada"));
        }

        /**
         * Test que comprueba que el método ventaEntradas de la clase VentaController devuelve un BadRequest cuando la petición es inválida.
         * @throws Exception Excepción lanzada por el método perform.
         */
        @Test
        void getVentasByUserEmailShouldReturnNotFoundWhenNoVentas() throws Exception {
                String email = "notfound@gmail.com";

                Mockito.when(ventaService.getVentasByUserEmail(email)).thenReturn(Collections.emptyList());

                mockMvc.perform(get("/venta/entradas/{email}", email))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.message").value("No se han encontrado ventas"));
        }

        /**
         * Test que comprueba que el método ventaEntradas de la clase VentaController devuelve un Ok cuando se encuent
         * @throws Exception Excepción lanzada por el método perform.
         */
        @Test
        void getVentasByUserEmailShouldReturnOkWhenVentasFound() throws Exception {
                String email = "example@gmail.com";

                Mockito.when(ventaService.getVentasByUserEmail(email)).thenReturn(Collections.singletonList(
                        new com.example.ventaService.dtos.DtoVenta()
                ));

                mockMvc.perform(get("/venta/entradas/{email}", email))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.message").value("Ventas encontradas"));
        }
}
