package com.salesianostriana.dam.testing.examen;

import com.salesianostriana.dam.testing.examen.dto.EditDatoMeteorologicoDto;
import com.salesianostriana.dam.testing.examen.dto.GetDatoMeteoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cglib.core.Local;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Sql(value = "classpath:import-data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class IntegrationTestTemplate {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;


	@BeforeEach
	void setUp() {
		restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}

	@Test
	void test() {
		GetDatoMeteoDto nuevo = new GetDatoMeteoDto("Sevilla", LocalDate.now(),10);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("qwertyuioplkjhgfdsazxcvbnm123456789");
		HttpEntity<GetDatoMeteoDto> entity = new HttpEntity<>(nuevo,headers);

		ResponseEntity<GetDatoMeteoDto> response = restTemplate.exchange("/meteo/add",
				HttpMethod.POST,
				entity,
				GetDatoMeteoDto.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertEquals(Objects.requireNonNull(response.getBody()).ciudad(), "Sevilla");
		assertEquals(response.getBody().precipitacion(), 10);
	}

}
