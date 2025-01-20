package com.example.eventoService;

import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Clase de test para toda aquella funcionalidad perteneciente a la capa de repositorio
 *
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024/12/10
 */
@SpringBootTest
public class EventoRepositoryTest {
    @Autowired
    private EventoRepository eventoRepository;

    //Prueba para conectar con la base de datos
    @Test
    public void testGuardarYRecuperarEvento() {
        Evento evento = Evento.builder()
                .nombre("Concierto Rock")
                .genero("Rock")
                .localidad("Barcelona")
                .recinto("Estadio Olímpico")
                .descripcion("Un gran concierto de rock")
                .fecha(LocalDate.now().plusDays(5))
                .precioMin(50.0)
                .precioMax(150.0)
                .build();

        Evento eventoGuardado = eventoRepository.save(evento);

        Optional<Evento> eventoRecuperado = eventoRepository.findById(eventoGuardado.getId());
        assertThat(eventoRecuperado).isPresent();
        assertThat(eventoRecuperado.get().getNombre()).isEqualTo("Concierto Rock");
        assertThat(eventoRecuperado.get().getGenero()).isEqualTo("Rock");
    }
}
