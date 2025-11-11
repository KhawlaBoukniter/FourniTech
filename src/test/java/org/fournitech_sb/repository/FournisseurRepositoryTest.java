package org.fournitech_sb.repository;

import org.fournitech_sb.model.Fournisseur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class FournisseurRepositoryTest {
    @Autowired
    FournisseurRepositoryInterface fournisseurRepository;

    @Test
    public void seveTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test");

        Fournisseur saved =  fournisseurRepository.save(fournisseur);
        assertNotNull(saved.getId());
    }

}
