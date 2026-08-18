package ru.netology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.netology.entity.AuthToken;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=none","spring.jpa.properties.hibernate.hbm2ddl.create_namespaces=true"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
		scripts = "/sql/init-data.sql")
class CloudServiceDiplomaApplicationTests {
	@LocalServerPort
	private Integer port;

	private final static String BASE_URL = "http://localhost:";
	private final static String PATH_LOGIN = "/cloud/login";
	private final static String PATH_LOGOUT = "/cloud/logout";
	private final static String PATH_FILE = "/cloud/file";
	private final static String PATH_LIST = "/cloud/list";

	@Autowired
	private TestRestTemplate restTemplate;

	private static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("test")
			.withUsername("postgres")
			.withPassword("postgres");

	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", db::getJdbcUrl);
		registry.add("spring.datasource.username", db::getUsername);
		registry.add("spring.datasource.password", db::getPassword);
		registry.add("spring.jpa.properties.hibernate.default_schema", () -> "cloudservice");
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
		registry.add("spring.jpa.properties.hibernate.hbm2ddl.create_namespaces", () -> "true");
	}

	@BeforeAll
    static void setUp() {
		db.start();
	}

	@Test
	void saveFilesTest() {
		System.out.println(db.getJdbcUrl());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("login", "test");
		requestBody.put("password", "test123");

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		//авторизуемся
		String url = BASE_URL + port + PATH_LOGIN;
		var response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AuthToken.class);
		System.out.println(response.getBody());
	}

}
