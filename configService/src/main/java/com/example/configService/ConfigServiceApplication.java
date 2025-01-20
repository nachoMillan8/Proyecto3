package com.example.configService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Clase principal del servicio, que configura y ejecuta un
 * servidor de configuración usando Spring Cloud Config Server
 * (vía @SpringBootApplication y @EnableConfigServer)
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-15
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServiceApplication.class, args);
	}

}
