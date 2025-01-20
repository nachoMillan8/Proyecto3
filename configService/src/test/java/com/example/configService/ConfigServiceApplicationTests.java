package com.example.configService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.environment.Environment;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del servicio de configuración
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-17
 * @version 1.0
 */
@SpringBootTest
class ConfigServiceApplicationTests {

	// Interfaz que usa Cloud Config Server para identificar configuraciones
	// h ttps://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_environment_repository
	@Autowired
	private EnvironmentRepository environmentRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void configServiceConectaBienTest(){
		// Archivo con nombre eventoService.properties
		String nombreServicio = "eventoService";
		// Perfil de Spring (no estamos usando ninguno especial)
		String profile = "default";
		// Rama del servidor (Git) → spring.cloud.config.server.git.default-label=features en el archivo
		String label = "features";

		// Encuentra algo que encaje con los tres parámetros dentro del Environment
		Environment environment = environmentRepository.findOne(nombreServicio, profile, label);

		// configService no es nulo
		assertNotNull(environment, "El entorno debe estar definido");
        assertFalse(environment.getPropertySources().isEmpty(), "Debe haber al menos un archivo" +
                "de configuración disponible.");

		// Para buscar al menos si tiene nombre
		assertTrue(
				environment.getPropertySources().stream().anyMatch(source -> source.getSource().containsKey("spring.application.name")),
				"El archivo de configuración debe contener 'spring.application.name'"
		);
	}

}
