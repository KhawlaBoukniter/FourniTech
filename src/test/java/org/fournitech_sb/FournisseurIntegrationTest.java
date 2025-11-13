package org.fournitech_sb;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.fournitech_sb.dto.FournisseurDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class FournisseurIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("fournitech_test")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    private FournisseurDto fournisseurDto;

    @BeforeEach
    void setUp() {
        fournisseurDto = new FournisseurDto();
        fournisseurDto.setNom("Fournisseur Test");
        fournisseurDto.setSociete("Test SA");
        fournisseurDto.setAdresse("addresse test");
        fournisseurDto.setEmail("test@email.com");
        fournisseurDto.setTelephone("0612345678");
        fournisseurDto.setVille("test");
        fournisseurDto.setIce("123456789");
    }

    @Test
    void shouldCreateAndGetFournisseur() {
        ResponseEntity<FournisseurDto> createResponse =
                restTemplate.postForEntity("/api/fournisseurs", fournisseurDto, FournisseurDto.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        FournisseurDto created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getNom()).isEqualTo(fournisseurDto.getNom());

        ResponseEntity<FournisseurDto> getResponse =
                restTemplate.getForEntity("/api/fournisseurs/" + created.getId(), FournisseurDto.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNom()).isEqualTo("Fournisseur Test");
    }

    @Test
    void shouldListFournisseurs() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/fournisseurs?page=0&size=5", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("content");
    }

    @Test
    void shouldDeleteFournisseur() {
        FournisseurDto dto = restTemplate.postForEntity("/api/fournisseurs", fournisseurDto, FournisseurDto.class).getBody();

        restTemplate.delete("/api/fournisseurs/" + dto.getId());

        ResponseEntity<FournisseurDto> response =
                restTemplate.getForEntity("/api/fournisseurs/" + dto.getId(), FournisseurDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
