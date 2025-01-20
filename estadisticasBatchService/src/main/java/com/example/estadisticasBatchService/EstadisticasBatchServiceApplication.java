package com.example.estadisticasBatchService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.estadisticasBatchService.feignClients")
public class EstadisticasBatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstadisticasBatchServiceApplication.class, args);
	}

}
