package com.example.usuarioService;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.errors.DuplicadoException;
import com.example.usuarioService.repository.UsuarioRepository;
import com.example.usuarioService.service.UsuarioServiceImpl;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private DtoUsuario dtoUsuario;
    private Usuario usuarioExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dtoUsuario = DtoUsuario.builder()
                .nombre("Juan")
                .apellido("Juanez")
                .email("juanito@gmail.com")
                .fechaNacimiento(LocalDate.of(1999,1,23))
                .build();

        usuarioExistente = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email(dtoUsuario.getEmail())
                .fechaNacimiento(dtoUsuario.getFechaNacimiento())
                .build();
    }

    @Test
    void testaddUsuario() {
        when(usuarioRepository.existsByEmail(dtoUsuario.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setId(1L);
            return usuario;
        });
        DtoUsuario resultado = usuarioService.saveUsuario(dtoUsuario);

        verify(usuarioRepository,times(1)).existsByEmail(dtoUsuario.getEmail());
        verify(usuarioRepository,times(1)).save(any(Usuario.class));

        assertNotNull(resultado);
        assertEquals(dtoUsuario.getNombre(), resultado.getNombre());
        assertEquals(dtoUsuario.getEmail(), resultado.getEmail());
    }


    @Test
    void testAddUsuarioLanzaExceptionSiExisteEmail(){
        when(usuarioRepository.existsByEmail(dtoUsuario.getEmail())).thenReturn(true);

        DuplicadoException exception = assertThrows(DuplicadoException.class, () -> usuarioService.saveUsuario(dtoUsuario));

        assertEquals("Email " + dtoUsuario.getEmail() + " ya existe", exception.getMessage());

        verify(usuarioRepository,times(1)).existsByEmail(dtoUsuario.getEmail());
        verify(usuarioRepository,never()).save(any(Usuario.class));
    }


    //comprobacion de json con getById
    @Test
    void verificaEstructuraJsonCorrecta() {
        //URI de la API
        RestAssured.baseURI = "http://localhost:7777/usuario";

        //ID a consultar
        Long usuarioId = 1L;

        given()
                .contentType(ContentType.JSON) //tipo de contenido de la solicitud
                .when()
                .get("/usuarios/{id}", usuarioId) //solicitud GET
                .then()
                .statusCode(200) //verifica que sea un 200
                .contentType(ContentType.JSON); //tipo de contenido de la respuesta

    }

    @Test
    @Transactional
    void verificaJsonPost(){
        RestAssured.baseURI = "http://localhost:7777/usuario";




        given()
                .contentType(ContentType.JSON)
                .body(dtoUsuario)

                .when()
                .post("/registro")

                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);
    }

}
