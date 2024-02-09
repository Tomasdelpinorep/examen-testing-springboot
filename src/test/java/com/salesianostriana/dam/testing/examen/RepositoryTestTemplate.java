package com.salesianostriana.dam.testing.examen;

import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql(value = "classpath:import-data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RepositoryTestTemplate {

	@Container
	@ServiceConnection
	static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
			.withUsername("testUser")
			.withPassword("testSecret")
			.withDatabaseName("testDatabase");

	@Autowired
	DatoMeteorologicoRepository repository;

	@Test
	void test() {
		boolean trueResult = repository.existePorFechaPoblacion(LocalDate.of(2024,2,9),"Sevilla");
		boolean falseResultByProvince = repository.existePorFechaPoblacion(LocalDate.of(2024,2,9),"sevilla");
		boolean falseResultByDate = repository.existePorFechaPoblacion(LocalDate.of(2024,2,8),"Sevilla");

		assertTrue(trueResult);
		assertFalse(falseResultByProvince);
		assertFalse(falseResultByDate);
	}

}
