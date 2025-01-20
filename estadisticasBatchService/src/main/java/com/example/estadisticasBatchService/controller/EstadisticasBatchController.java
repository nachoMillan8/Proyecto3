package com.example.estadisticasBatchService.controller;

import com.example.estadisticasBatchService.repository.EstadisticaVentaRepository;
import com.example.estadisticasBatchService.service.EstadisticasService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadisticasBatchController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job estadisticasJob;

    @GetMapping("/procesar-estadisticas")
    public String procesarEstadisticas(@RequestParam String fecha) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fecha", fecha)
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(estadisticasJob, jobParameters);
            return "Proceso de estadísticas iniciado para la fecha: " + fecha;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al iniciar el proceso. " + e.getMessage();
        }
    }
}

// Para simular el proceso en Postman