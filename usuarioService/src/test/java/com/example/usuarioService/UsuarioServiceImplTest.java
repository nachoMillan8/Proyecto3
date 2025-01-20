package com.example.usuarioService;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.repository.UsuarioRepository;
import com.example.usuarioService.service.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private DtoUsuario dtoUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //inicializamos un dto de ejemplo
        dtoUsuario = DtoUsuario.builder()
                .nombre("John")
                .apellido("Doe")
                .email("john.doe@example.com")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    void noGuardaUsuarioDuplicado() {
        //simulamos que el email ya existe en el repositorio
        when(usuarioRepository.existsByEmail(dtoUsuario.getEmail())).thenReturn(true);

        var errores = usuarioService.validatePost(dtoUsuario);

        //verificamos que hay errores relacionados con el email duplicado
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.getMessage().equals("Email duplicado")));

        //aseguramos que no se intente guardar
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void guardaUsuarioCorrectamente() {
        //simulamos que el email no existe en el repositorio
        when(usuarioRepository.existsByEmail(dtoUsuario.getEmail())).thenReturn(false);

        //creamos el usuario a guardar
        Usuario usuario = Usuario.builder()
                .nombre(dtoUsuario.getNombre())
                .apellido(dtoUsuario.getApellido())
                .email(dtoUsuario.getEmail())
                .fechaNacimiento(dtoUsuario.getFechaNacimiento())
                .build();

        //simulamos la respuesta del repositorio al guardar el usuario
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        //guardamos el usuario
        DtoUsuario resultado = usuarioService.saveUsuario(dtoUsuario);

        //verificamos que el resultado coincide con el DTO original
        assertNotNull(resultado);
        assertEquals(dtoUsuario.getNombre(), resultado.getNombre());
        assertEquals(dtoUsuario.getApellido(), resultado.getApellido());
        assertEquals(dtoUsuario.getEmail(), resultado.getEmail());

        //verificamos que el método save se llama exactamente una vez
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void actualizaUsuarioCorrectamente() {
        //id del usuario a actualizar
        Long usuarioId = 1L;

        //simulamos un usuario existente en la base de datos
        Usuario usuarioExistente = Usuario.builder()
                .id(usuarioId)
                .nombre("Jose")
                .apellido("gonzalez")
                .email("jose.doe@example.com")
                .fechaNacimiento(LocalDate.of(1985, 5, 15))
                .build();

        //dto con los datos actualizados
        DtoUsuario dtoUsuarioActualizado = DtoUsuario.builder()
                .nombre("Jose Updated")
                .apellido("gonzalez Updated")
                .email("jose.updated@example.com")
                .fechaNacimiento(LocalDate.of(1985, 5, 15))
                .build();

        //simulamos la respuesta del repositorio al buscar el usuario
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));

        //simulamos la respuesta del repositorio al guardar el usuario actualizado
        Usuario usuarioActualizado = Usuario.builder()
                .id(usuarioId)
                .nombre(dtoUsuarioActualizado.getNombre())
                .apellido(dtoUsuarioActualizado.getApellido())
                .email(dtoUsuarioActualizado.getEmail())
                .fechaNacimiento(dtoUsuarioActualizado.getFechaNacimiento())
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        //ejecutamos el método de actualización
        DtoUsuario resultado = usuarioService.updateUsuario(usuarioId, dtoUsuarioActualizado);

        //validamos los resultados
        assertNotNull(resultado);
        assertEquals(dtoUsuarioActualizado.getNombre(), resultado.getNombre());
        assertEquals(dtoUsuarioActualizado.getApellido(), resultado.getApellido());
        assertEquals(dtoUsuarioActualizado.getEmail(), resultado.getEmail());

        //verificamos que se llamaron los métodos correctos en el repositorio
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuarioCorrectamenteTest(){
        Long id = 5L;

        Usuario usuarioEjemplo = Usuario.builder()
                .id(id)
                .nombre("Nissi")
                .apellido("M.")
                .email("ejemplito@gmail.com")
                .fechaNacimiento(LocalDate.of(1999, 6, 4))
                .build();

        when(usuarioRepository.existsById(id)).thenReturn(true);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioEjemplo));

        usuarioService.deleteById(id);

        verify(usuarioRepository, times(1)).existsById(id);
        verify(usuarioRepository, times(1)).deleteById(id);

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }



}

