package org.fournitech_sb.repository;

import org.fournitech_sb.model.Produit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProduitRepositoryTest {

    @Autowired
    private ProduitRepositoryInterface produitRepository;

    @Test
    void saveTest() {
        Produit produit = new Produit();
        produit.setNom("Test");
        produit.setStockActuel(10);
        produit.setPrixUnit(99.9);

        Produit savedProduit = produitRepository.save(produit);

        assertThat(savedProduit).isNotNull();
        assertThat(savedProduit.getId()).isNotNull();
        assertThat(savedProduit.getNom()).isEqualTo("Test");
    }


}
