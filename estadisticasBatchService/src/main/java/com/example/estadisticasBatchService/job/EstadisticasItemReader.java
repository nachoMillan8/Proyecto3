package com.example.estadisticasBatchService.job;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class EstadisticasItemReader implements ItemReader<Map<String, Double>> {

    private Iterator<Map<String, Double>> iterator;

    @Override
    public Map<String, Double> read() throws Exception {
        if (iterator == null || !iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

    public void setData(Iterator<Map<String, Double>> iterator) {
        this.iterator = iterator;
    }
}
