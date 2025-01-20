package com.example.eventoService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Clase principal del servicio Evento
 *
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024/12/10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EventoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoServiceApplication.class, args);
	}

}
