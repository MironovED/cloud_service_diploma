package ru.netology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.netology.dto.FileInfo;
import ru.netology.entity.AuthToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=none","spring.jpa.properties.hibernate.hbm2ddl.create_namespaces=true"})
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

	private static final PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:latest")
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
    static void setUp() throws IOException {
		db.start();
		var path = Path.of("files");
		if (Files.exists(path)) {
			Files.walk(path)
					.sorted(Comparator.reverseOrder())
					.forEach(p -> {
						try {
							Files.deleteIfExists(p);
						} catch (IOException e) {
							System.err.println("Не удалось удалить " + p + ": " + e.getMessage());
						}
					});
		}
	}

	@Test
	void end2EndTest() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("login", "test@test.com");
		requestBody.put("password", "qwerty12345");
		HttpEntity<Map<String, String>> requestEntityLogin = new HttpEntity<>(requestBody, headers);

		//авторизуемся
		var response = restTemplate.exchange(
				BASE_URL + port + PATH_LOGIN,
				HttpMethod.POST,
				requestEntityLogin,
				AuthToken.class);
        String token = response.getBody().getToken();

		// Грузим файл
		ClassPathResource fileResource = new ClassPathResource("testfiles/file5.txt");
		MultiValueMap<String, Object> bodyUploadFile = new LinkedMultiValueMap<>();
		bodyUploadFile.add("file5", "file5.txt");
		bodyUploadFile.add("file", new FileSystemResource(fileResource.getFile()));

		headers.set("auth-token", "Bearer " + token);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, Object>> requestEntityUploadFile =
				new HttpEntity<>(bodyUploadFile, headers);

		var responseUploadFile = restTemplate.exchange(
				BASE_URL + port + PATH_FILE + "?filename=file5",
				HttpMethod.POST,
				requestEntityUploadFile,
				String.class);
		assertEquals("Success upload", responseUploadFile.getBody());

		// Смотрим что файл загружен
		headers.setContentType(MediaType.APPLICATION_JSON);
		var reponseGetFile = restTemplate.exchange(
				BASE_URL + port + PATH_LIST,
				HttpMethod.GET,
				new HttpEntity<>(headers),
				FileInfo[].class);
		assertEquals(1, reponseGetFile.getBody().length);

		// удалим файл
		var reponseDeleteFile = restTemplate.exchange(
				BASE_URL + port + PATH_FILE + "?filename=file5",
				HttpMethod.DELETE,
				new HttpEntity<>(headers),
				String.class);
		assertEquals("Success deleted", reponseDeleteFile.getBody());

		// разлогинимся
		var responseLogout = restTemplate.exchange(
				BASE_URL + port + PATH_LOGOUT,
				HttpMethod.POST,
				new HttpEntity<>(headers),
				String.class);
		assertEquals("Success logout", responseLogout.getBody());
	}
}
