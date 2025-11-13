package org.fournitech_sb;

import org.fournitech_sb.dto.CommandeDto;
import org.fournitech_sb.dto.ProduitCommandeDto;
import org.fournitech_sb.model.*;
import org.fournitech_sb.repository.CommandeRepositoryInterface;
import org.fournitech_sb.repository.ProduitCommandeRepositoryInterface;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import org.fournitech_sb.repository.FournisseurRepositoryInterface;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CommandeIntegrationTest {

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

    @Autowired
    private FournisseurRepositoryInterface fournisseurRepository;

    @Autowired
    private ProduitRepositoryInterface produitRepository;

    @Autowired
    private CommandeRepositoryInterface commandeRepository;

    @Autowired
    private ProduitCommandeRepositoryInterface produitCommandeRepository;

    private Fournisseur fournisseur;
    private Produit produit;

    @BeforeEach
    void setUp() {
        produitCommandeRepository.deleteAll();
        commandeRepository.deleteAll();
        produitRepository.deleteAll();
        fournisseurRepository.deleteAll();

        fournisseur = new Fournisseur();
        fournisseur.setNom("Fournisseur Test");
        fournisseur = fournisseurRepository.save(fournisseur);

        produit = new Produit();
        produit.setNom("Produit Test");
        produit.setStockActuel(10);
        produitRepository.save(produit);
    }

    @Test
    void shouldCreateCommande() {
        CommandeDto dto = new CommandeDto();
        dto.setFournisseurId(fournisseur.getId());

        ProduitCommandeDto pc = new ProduitCommandeDto();
        pc.setProduitId(produit.getId());
        pc.setQuantite(5);
        pc.setPrixUnit(20.0);
        dto.setProduitCommandes(List.of(pc));

        ResponseEntity<CommandeDto> response = restTemplate.postForEntity("/api/commandes", dto, CommandeDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatutCommande()).isEqualTo(StatutCommande.EN_ATTENTE);
    }

    
}
