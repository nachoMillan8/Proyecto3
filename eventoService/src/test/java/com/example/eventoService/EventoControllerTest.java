package com.example.eventoService;

import com.example.eventoService.controller.EventoController;
import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.dto.ResponseMessage;
import com.example.eventoService.service.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clase de test para toda aquella funcionalidad perteneciente a la capa de control
 *
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024/12/12
 */
@WebMvcTest(EventoController.class)
@Import(EventoControllerTest.MockConfiguration.class)
public class EventoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EventoService eventoService;
    static class MockConfiguration {
        @Bean
        public EventoService eventoService() {
            return mock(EventoService.class);
        }
    }
    @Test
    void addEventoShouldReturnCreatedWhenInputIsValid() throws Exception {
        DtoEvento dtoEvento = DtoEvento.builder()
                .nombre("Concierto Rock")
                .descripcion("Concierto en vivo de bandas locales")
                .genero("Rock")
                .localidad("Madrid")
                .recinto("Wanda Metropolitano")
                .fecha("15/12/2024")
                .precioMin(30.50)
                .precioMax(100.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/evento/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEvento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Evento creado"))
                .andExpect(jsonPath("$.cause").value("El evento Concierto Rock ha sido creado correctamente"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()));
    }

    @Test
    void addEventoShouldReturnBadRequestWhenInputIsInvalid() throws Exception {
        DtoEvento dtoEvento = DtoEvento.builder()
                .nombre("")
                .descripcion("Concierto en vivo de bandas locales")
                .genero("Rock")
                .localidad("Madrid")
                .recinto("Wanda Metropolitano")
                .fecha("15/12/2024")
                .precioMin(30.50)
                .precioMax(100.00)
                .build();
        List<ResponseMessage> errores = List.of(
                ResponseMessage.builder()
                        .message("Nombre no puede estar vacío")
                        .cause("Se ha proporcionado un nombre vacío")
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build()
        );

        when(eventoService.validate(any(DtoEvento.class),false)).thenReturn(errores);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/evento/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEvento)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error en la petición"))
                .andExpect(jsonPath("$.cause").value("La petición no es válida"))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()));
    }
}
