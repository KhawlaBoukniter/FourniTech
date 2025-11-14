package org.fournitech_sb;

import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.model.*;
import org.fournitech_sb.repository.CommandeRepositoryInterface;
import org.fournitech_sb.repository.MouvementStockRepositoryInterface;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class MouvementStockIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("fournitech_test")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProduitRepositoryInterface produitRepository;

    @Autowired
    private CommandeRepositoryInterface commandeRepository;

    @Autowired
    private MouvementStockRepositoryInterface mouvementStockRepository;

    private Produit produit;
    private Commande commande;

    @BeforeEach
    void setup() {
        mouvementStockRepository.deleteAll();
        commandeRepository.deleteAll();
        produitRepository.deleteAll();

        produit = new Produit();
        produit.setNom("Produit Test");
        produit.setStockActuel(50);
        produitRepository.save(produit);

        commande = new Commande();
        commande.setStatutCommande(StatutCommande.EN_ATTENTE);
        commandeRepository.save(commande);

        MouvementStock m1 = new MouvementStock();
        m1.setProduit(produit);
        m1.setQuantiteMouvement(10);
        m1.setTypeMouvement(TypeMouvement.ENTREE);
        m1.setDateMouvement(LocalDate.now());
        mouvementStockRepository.save(m1);

        MouvementStock m2 = new MouvementStock();
        m2.setProduit(produit);
        m2.setCommande(commande);
        m2.setQuantiteMouvement(5);
        m2.setTypeMouvement(TypeMouvement.SORTIE);
        m2.setDateMouvement(LocalDate.now());
        mouvementStockRepository.save(m2);
    }

    

}
