package com.example.eventoService;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import com.example.eventoService.service.EventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Clase de test para toda aquella funcionalidad perteneciente a la capa de servicios
 *
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024/12/10
 */
@SpringBootTest
public class EventoServiceImplTest {
    @Mock
    EventoRepository eventoRepository;
    @InjectMocks
    EventoServiceImpl eventoService;

    private DtoEvento dtoEvento;

    private Evento eventoExistente;

    @BeforeEach
    public void setUp() {
        openMocks(this);

        dtoEvento = DtoEvento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(String.valueOf(LocalDate.now().plusDays(10)))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();

        eventoExistente = Evento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(LocalDate.parse(dtoEvento.getFecha()))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();
    }
    @Test
    public void testGetDetallesEvento() {
        when(eventoRepository.findById(1L)).thenReturn(null);
        assertTrue(eventoRepository.findById(1L) == null);
    }

    @Test
    public void testSaveEventoCorrectamente() {
        Evento evento = Evento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(LocalDate.from(LocalDate.now().plusDays(10)))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();

        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        DtoEvento result = eventoService.saveEvento(dtoEvento);

        verify(eventoRepository, times(1)).save(any(Evento.class));
        assert(result.getNombre().equals(evento.getNombre()));
        assert(result.getGenero().equals(evento.getGenero()));
        assert(result.getLocalidad().equals(evento.getLocalidad()));
        assert(result.getRecinto().equals(evento.getRecinto()));
        assert(result.getPrecioMin() == evento.getPrecioMin());
        assert(result.getPrecioMax() == evento.getPrecioMax());
    }

    @Test
    public void testSaveEventoLanzaExcepcionSiExiste() {
        when(eventoRepository.findByNombreAndGeneroAndFecha(
                dtoEvento.getNombre(),
                dtoEvento.getGenero(),
                LocalDate.parse(dtoEvento.getFecha())))
                .thenReturn(Optional.of(eventoExistente));
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoExistente);
        eventoService.saveEvento(dtoEvento);

        verify(eventoRepository, times(1)).findByNombreAndGeneroAndFecha(dtoEvento.getNombre(), dtoEvento.getGenero(), LocalDate.parse(dtoEvento.getFecha()));

        verify(eventoRepository, never()).save(any(Evento.class));
    }

    @Test
    public void listaVaciaEventosTest(){
        when(eventoRepository.findAll()).thenReturn(List.of());
        List<DtoEvento> resultado = eventoService.listarEventos();

        assertNotNull(resultado, "La lista devuelta no debe ser null.");
        assertTrue(resultado.isEmpty(), "La lista devuelta debe estar vacía.");

        verify(eventoRepository, times(1)).findAll();
    }
    @Test
    public void comprobarUpdateEvento(){
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoExistente));
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoExistente);

        DtoEvento dtoEvento = DtoEvento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(String.valueOf(LocalDate.now().plusDays(10)))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();

        DtoEvento result = eventoService.updateEvento(1L, dtoEvento);

        assert(result.getNombre().equals(eventoExistente.getNombre()));
        assert(result.getGenero().equals(eventoExistente.getGenero()));
        assert(result.getLocalidad().equals(eventoExistente.getLocalidad()));
        assert(result.getRecinto().equals(eventoExistente.getRecinto()));
        assert(result.getPrecioMin() == eventoExistente.getPrecioMin());
        assert(result.getPrecioMax() == eventoExistente.getPrecioMax());
    }
    @Test
    public void comprobarNoSeDuplicaEvento(){
        when(eventoRepository.existEvento(
                dtoEvento.getNombre(),
                dtoEvento.getGenero(),
                LocalDate.parse(dtoEvento.getFecha()),
                dtoEvento.getLocalidad(),
                dtoEvento.getRecinto(),
                dtoEvento.getPrecioMin(),
                dtoEvento.getPrecioMax(),
                dtoEvento.getDescripcion())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> eventoService.saveEvento(dtoEvento));
    }
    @Test
    public void listaCorrectaEventosTest(){
        List<Evento> eventos = List.of(
            Evento.builder()
                .nombre("Concierto Pop")
                .genero("Pop")
                .localidad("Mallorca")
                .recinto("Musichall")
                .descripcion("descripcion")
                .fecha(LocalDate.from(LocalDateTime.now().plusDays(10)))
                .precioMin(30.0)
                .precioMax(100.0)
                .build(),
            Evento.builder()
                 .nombre("Concierto EDM")
                 .genero("EDM")
                 .localidad("Madrid")
                 .recinto("Palacio de Deportes")
                 .descripcion("descripcion")
                 .fecha(LocalDate.from(LocalDateTime.now().plusDays(10)))
                 .precioMin(55.0)
                 .precioMax(80.0)
                 .build()
        );

        when(eventoRepository.findAll()).thenReturn(eventos);

        List<DtoEvento> resultado = eventoService.listarEventos();

        verify(eventoRepository, times(1)).findAll();

        assert(resultado.size() == eventos.size());

        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            DtoEvento dtoEvento1 = resultado.get(i);

            assert(evento.getNombre().equals(dtoEvento1.getNombre()));
            assert(evento.getGenero().equals(dtoEvento1.getGenero()));
            assert(evento.getLocalidad().equals(dtoEvento1.getLocalidad()));
            assert(evento.getRecinto().equals(dtoEvento1.getRecinto()));
            assert(evento.getDescripcion().equals(dtoEvento1.getDescripcion()));
            assert(evento.getFecha().equals(LocalDate.parse(dtoEvento1.getFecha())));
            assert(evento.getPrecioMin() == dtoEvento1.getPrecioMin());
            assert(evento.getPrecioMax() == dtoEvento1.getPrecioMax());
        }
    }

    @Test
    public void soloDtoEventoTest(){
        List<Evento> eventos = List.of(
                Evento.builder()
                        .nombre("Concierto Pop")
                        .genero("Pop")
                        .localidad("Mallorca")
                        .recinto("Musichall")
                        .descripcion("descripcion")
                        .fecha(LocalDate.from(LocalDateTime.now().plusDays(10)))
                        .precioMin(30.0)
                        .precioMax(100.0)
                        .build(),
                Evento.builder()
                        .nombre("Concierto EDM")
                        .genero("EDM")
                        .localidad("Madrid")
                        .recinto("Palacio de Deportes")
                        .descripcion("descripcion")
                        .fecha(LocalDate.from(LocalDateTime.now().plusDays(10)))
                        .precioMin(55.0)
                        .precioMax(80.0)
                        .build()
        );

        when(eventoRepository.findAll()).thenReturn(eventos);
        List<DtoEvento> resultado = eventoService.listarEventos();

        verify(eventoRepository, times(1)).findAll();
        assertFalse(resultado.isEmpty());

        for (DtoEvento dtoEvento1 : resultado) {
            assertTrue(dtoEvento1 instanceof DtoEvento, "Se ha devuelto algo que no era un DtoEvento.");
        }
    }

    @Test
    public void deleteEventoNoExisteTest() {
        Long idEjemplo = 648L;
        when(eventoRepository.existsById(idEjemplo)).thenReturn(false);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> eventoService.deleteById(idEjemplo)
        );

        String mensajeEx = "El evento con ID " + idEjemplo + " no existe.";
        assertEquals(mensajeEx, ex.getMessage());
        verify(eventoRepository, never()).deleteById(idEjemplo);
    }

    @Test
    public void deleteEventoCorrectamenteTest() {
        Long idEjemplo = 648L;
        Evento eventoEjemplo = Evento.builder()
                .id(idEjemplo)
                .nombre("Concierto 珂拉琪")
                .genero("Folk-rock")
                .localidad("Madrid")
                .recinto("Sala Caracol")
                .descripcion("ojalá")
                .fecha(LocalDate.from(LocalDate.now().plusDays(10)))
                .precioMin(10)
                .precioMax(50)
                .build();
        DtoEvento dtoEjemplo = DtoEvento.builder()
                .id(idEjemplo)
                .nombre("Concierto 珂拉琪")
                .genero("Folk-rock")
                .localidad("Madrid")
                .recinto("Sala Caracol")
                .descripcion("ojalá")
                .fecha(String.valueOf(LocalDate.now().plusDays(10)))
                .precioMin(10)
                .precioMax(50)
                .build();
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoEjemplo);
        DtoEvento resultado = eventoService.saveEvento(dtoEvento);
        verify(eventoRepository, times(1)).save(any(Evento.class));

        assert(resultado.getNombre().equals(eventoEjemplo.getNombre()));
        assert(resultado.getGenero().equals(eventoEjemplo.getGenero()));
        assert(resultado.getLocalidad().equals(eventoEjemplo.getLocalidad()));
        assert(resultado.getRecinto().equals(eventoEjemplo.getRecinto()));
        assert(resultado.getPrecioMin() == eventoEjemplo.getPrecioMin());
        assert(resultado.getPrecioMax() == eventoEjemplo.getPrecioMax());

        when(eventoRepository.existsById(eventoEjemplo.getId())).thenReturn(true);
        eventoService.deleteById(eventoEjemplo.getId());

        verify(eventoRepository, times(1)).deleteById(eventoEjemplo.getId());
        verify(eventoRepository, times(1)).existsById(eventoEjemplo.getId());
    }

    @Test
    void testFindByNombre() {
        String nombreEvento = "Concierto de Rock";
        Evento eventoMock = Evento.builder()
                .id(1L)
                .nombre(nombreEvento)
                .descripcion("Un concierto increíble")
                .genero("Rock")
                .localidad("Madrid")
                .recinto("Estadio Santiago Bernabéu")
                .fecha(LocalDate.now())
                .precioMin(30.00)
                .precioMax(100.00)
                .build();
        when(eventoRepository.findByNombre(nombreEvento)).thenReturn(List.of(eventoMock));
        List<DtoEvento> resultado = eventoService.findByNombre(nombreEvento);

        assertFalse(resultado.isEmpty(), "El resultado no debe estar vacío");
        assertEquals(1, resultado.size(), "Debe haber un evento");
        assertEquals(nombreEvento, resultado.get(0).getNombre(), "El nombre del evento debe coincidir");

        verify(eventoRepository, times(1)).findByNombre(nombreEvento);
    }

}
