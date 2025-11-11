package org.fournitech_sb.service;

import org.fournitech_sb.model.Fournisseur;
import org.fournitech_sb.repository.FournisseurRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FournisseurServiceTest {

    @Mock
    FournisseurRepositoryInterface fournisseurRepository;

    @InjectMocks
    FournisseurServiceImpl fournisseurService;

    @Test
    public void addFournisseurTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);
        fournisseur.setNom("Test");

        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);

        Fournisseur rslt = fournisseurService.addFournisseur(fournisseur);

        assertNotNull(rslt);
        assertEquals(fournisseur.getNom(), rslt.getNom());
        verify(fournisseurRepository).save(fournisseur);
    }

    @Test
    public void updateFournisseurTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);
        fournisseur.setNom("Test");

        Fournisseur updated =  new Fournisseur();
        updated.setNom("Updated");

        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(updated);

        Fournisseur rslt = fournisseurService.updateFournisseur(updated);
        assertNotNull(rslt);
        assertEquals(updated.getNom(), rslt.getNom());
        verify(fournisseurRepository).save(any(Fournisseur.class));
    }

    @Test
    public void findAllTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);

        Page<Fournisseur> pageMock = new PageImpl<>(List.of(fournisseur));
        when(fournisseurRepository.findAll(any(Pageable.class))).thenReturn(pageMock);

        Page<Fournisseur> result = fournisseurService.findAllFournisseur(0, 2, "id", true);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        verify(fournisseurRepository).findAll(any(Pageable.class));
    }

    @Test
    public void getByIdTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);
        fournisseur.setNom("Test");
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));

        Fournisseur rslt = fournisseurService.findFournisseurById(1L);
        assertEquals("Test", rslt.getNom());
        verify(fournisseurRepository).findById(1L);
    }

    @Test
    public void deleteFournusseurTest() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1L);

        doNothing().when(fournisseurRepository).delete(fournisseur);
        fournisseurService.deleteFournisseur(fournisseur);

        verify(fournisseurRepository).delete(fournisseur);
    }
}
