package com.example.ventaService.feignClient;

import com.example.ventaService.model.Evento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface to communicate with the Evento service
 */
@FeignClient(name = "eventoService",url = "localhost:8888/evento")//PONEMOS EL NOMBRE DEL MICROSERVICIO (EL QUE PONEMOS EN EL PROPERTIES)
public interface EventoClient {
    /**
     * Method to get an event by its id
     * @param idEvento Id of the event
     * @return  Evento
     */
    @GetMapping("/details/{id}")
    Evento getEventoById(@PathVariable("id") Long idEvento);
}
