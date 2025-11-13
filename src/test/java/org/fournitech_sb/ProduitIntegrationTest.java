package org.fournitech_sb;

import org.fournitech_sb.dto.ProduitDto;
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
public class ProduitIntegrationTest {

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

    private ProduitDto produitDto;

    @BeforeEach
    void setUp() {
        produitDto = new ProduitDto();
        produitDto.setNom("Produit Test");
        produitDto.setStockActuel(10);
        produitDto.setPrixUnit(100.0);
        produitDto.setDescription("Description du produit");
        produitDto.setCategorie("Electronique");
    }

    @Test
    void shouldCreateAndGetProduit() {
        ResponseEntity<ProduitDto> createResponse =
                restTemplate.postForEntity("/api/produits", produitDto, ProduitDto.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProduitDto created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getNom()).isEqualTo(produitDto.getNom());

        ResponseEntity<ProduitDto> getResponse =
                restTemplate.getForEntity("/api/produits/" + created.getId(), ProduitDto.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNom()).isEqualTo("Produit Test");
    }

    
}
