package com.salesianostriana.dam.testing.examen;

import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"integration-test"})
@Sql(value = "classpath:import-data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ServicioMeteorologico servicioMeteorologico;

    @Test
    void mediaMensualPorPoblacionTest() throws Exception {
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("Sevilla", 10d);

        when(servicioMeteorologico.mediaMensual(anyString())).thenReturn(mockMap);

        mvc.perform(MockMvcRequestBuilders.get("/meteo/mes/{ciudad}", "Sevilla")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.ciudad", is("Sevilla")))
                .andExpect(jsonPath("$.items[0].media", is(10)));
    }
}
