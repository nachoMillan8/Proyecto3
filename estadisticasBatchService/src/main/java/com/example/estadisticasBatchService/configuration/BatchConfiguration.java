package com.example.estadisticasBatchService.configuration;

import com.example.estadisticasBatchService.dtos.DtoVenta;
import com.example.estadisticasBatchService.entity.EstadisticaVenta;
import com.example.estadisticasBatchService.job.EstadisticaItemWriter;
import com.example.estadisticasBatchService.job.EstadisticasItemReader;
import com.example.estadisticasBatchService.job.VentaItemReader;
import com.example.estadisticasBatchService.job.MediaPrecioProcessor;
import com.example.estadisticasBatchService.repository.EventoExtractor;
import jakarta.inject.Qualifier;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Configuration
//@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public ItemReader<EstadisticaVenta> reader(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT v.id_evento, v.precio FROM venta_entity v;";
        List<EstadisticaVenta> listaBD = JdbcTemplate.query(query, new EventoExtractor());
        System.out.println("Tamaño del listado :" + listaBD.size());
        return new ListItemReader<>(listaBD);
    }

    @Bean
    public ItemWriter<DtoVenta> writer(DataSource dataSource) {
        return null;
    }

//    // Paso 1: Localizar los datos (para identificar la ubicación de los datos)
//    @Bean
//    public Step localizarDatosStep(JobRepository jobRepository,
//                                   PlatformTransactionManager transactionManager,
//                                   VentaItemReader reader) {
//        return new StepBuilder("localizarDatosStep", jobRepository)
//                .<List<DtoVenta>, List<DtoVenta>>chunk(1, transactionManager)
//                .reader(reader)
//                .writer(items -> {
//                    // Este paso no hace nada más que identificar dónde están los datos
//                })
//                .build();
//    }
//
//    @Bean
//    public Step calcularMediasStep(JobRepository jobRepository,
//                                   PlatformTransactionManager transactionManager,
//                                   VentaItemReader reader,
//                                   MediaPrecioProcessor processor,
//                                   EstadisticaItemWriter writer) {
//        return new StepBuilder("calcularMediasStep", jobRepository)
//                .<List<DtoVenta>, Map<String, Double>>chunk(1, transactionManager)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//
//    @Bean
//    public Step guardarDatosEstadisticasStep(JobRepository jobRepository,
//                                             PlatformTransactionManager transactionManager,
//                                             EstadisticasItemReader reader,
//                                             EstadisticaItemWriter writer) {
//        return new StepBuilder("guardarDatosEstadisticasStep", jobRepository)
//                .<Map<String, Double>, Map<String, Double>>chunk(1, transactionManager)
//                .reader(reader)
//                .writer(writer)
//                .build();
//    }

    @Bean
    public Step pasoStep(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager,
                         ItemReader<EstadisticaVenta> reader,
                         ItemWriter writer,
                         MediaPrecioProcessor processor)
    {
        return new StepBuilder("pasoStep", jobRepository)
                .<EstadisticaVenta, DtoVenta>chunk(1, transactionManager)
                .reader(reader)
            .writer(writer)
            .processor(processor)
            .build();



    }

    @Bean
    public Job estadisticasJob(JobRepository jobRepository,
                               @Qualifier("pasoStep") Step pasoStep)
    {
        return new JobBuilder("estadisticasJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(pasoStep)
                .end()
                .build();
    }
}
