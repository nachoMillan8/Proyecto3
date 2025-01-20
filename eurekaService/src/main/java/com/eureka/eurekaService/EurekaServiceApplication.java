package com.eureka.eurekaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Clase principal del servicio, que lo señaliza como
 * servidor de Eureka y lo pone en comunicación usando EurekaServer
 * (vía @EnableEurekaServer y @SpringBootApplication)
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-16
 * @version 1.0
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceApplication.class, args);
	}

}
