package com.example.estadisticasBatchService.repository;

import com.example.estadisticasBatchService.entity.EstadisticaVenta;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;

public class EventoExtractor implements ResultSetExtractor<List<EstadisticaVenta>> {

    @Override
    public List<EstadisticaVenta> extractData(ResultSet rs) throws SQLException {
        Map<Long, EstadisticaVenta> eventoMap = new HashMap<>();

        while (rs.next()) {
            Long eventoId = rs.getLong("id");

            EstadisticaVenta evento = eventoMap.get(eventoId);


            evento = new EstadisticaVenta();
            evento.setId(eventoId);
            evento.setPrecio(rs.getDouble("precio"));
        }

        return null;
    }
}