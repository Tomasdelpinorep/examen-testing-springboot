package com.salesianostriana.dam.testing.examen;

import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    ServicioMeteorologico servicioMeteorologico;

    @Mock
    DatoMeteorologicoRepository repository;

    @Test
    void mediaMensualTest(){
        DatoMeterologicoPK dato1PK = new DatoMeterologicoPK("Sevilla", LocalDate.of(2024,2,9));
        DatoMeterologicoPK dato2PK = new DatoMeterologicoPK("Sevilla", LocalDate.of(2024,2,9));
        DatoMeterologicoPK dato3PK = new DatoMeterologicoPK("Cádiz", LocalDate.of(2024,2,9));

        DatoMeteorologico dato1 = DatoMeteorologico.builder()
                .id(dato1PK).precipitacion(10).build();
        DatoMeteorologico dato2 = DatoMeteorologico.builder()
                .id(dato2PK).precipitacion(10).build();
        DatoMeteorologico dato3 = DatoMeteorologico.builder()
                .id(dato3PK).precipitacion(10).build();

        when(repository.buscarPorPoblacion(anyString())).thenReturn(List.of(dato1,dato2));

        Map<String, Double> result = servicioMeteorologico.mediaMensual("Sevilla");

        Assertions.assertEquals(result.get("FEBRERO"),10);
    }
}
