package org.fournitech_sb.repository;

import org.fournitech_sb.model.Fournisseur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void findByIdTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test");

        Fournisseur saved =  fournisseurRepository.save(fournisseur);
        Optional<Fournisseur> found = fournisseurRepository.findById(fournisseur.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getNom(), saved.getNom());

    }

    @Test
    void findAllTest() {
        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setNom("Test1");

        Fournisseur fournisseur2 = new Fournisseur();
        fournisseur2.setNom("Test2");

        fournisseurRepository.save(fournisseur1);
        fournisseurRepository.save(fournisseur2);

        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        assertThat(fournisseurs).hasSizeGreaterThanOrEqualTo(2);
    }
    
}
