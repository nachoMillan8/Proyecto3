package com.example.estadisticasBatchService.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.estadisticasBatchService.repository")
@EntityScan(basePackages = "com.example.estadisticasBatchService.entity")
public class AppConfig {
}
