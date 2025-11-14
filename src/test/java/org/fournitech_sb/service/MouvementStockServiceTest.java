package org.fournitech_sb.service;

import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.exception.ResourceNotFoundException;
import org.fournitech_sb.mapper.MouvementStockMapper;
import org.fournitech_sb.model.Commande;
import org.fournitech_sb.model.MouvementStock;
import org.fournitech_sb.model.Produit;
import org.fournitech_sb.model.TypeMouvement;
import org.fournitech_sb.repository.CommandeRepositoryInterface;
import org.fournitech_sb.repository.MouvementStockRepositoryInterface;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MouvementStockServiceTest {

    @Mock
    private MouvementStockRepositoryInterface mouvementStockRepository;

    @Mock
    private ProduitRepositoryInterface produitRepository;

    @Mock
    private CommandeRepositoryInterface commandeRepository;

    @Mock
    private MouvementStockMapper mouvementStockMapper;

    @InjectMocks
    private MouvementStockServiceImpl mouvementStockService;

    private Produit produit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produit = new Produit();
        produit.setId(1L);
        produit.setStockActuel(10);
        produit.setCump(20.0);
    }

    @Test
    void testCreateMouvement_Entree() {
        when(produitRepository.save(any())).thenReturn(produit);

        mouvementStockService.createMouvement(produit, 5, 30.0, TypeMouvement.ENTREE, null);

        verify(produitRepository, times(1)).save(produit);
        verify(mouvementStockRepository, times(1)).save(any(MouvementStock.class));

        assertEquals(10, produit.getStockActuel());
    }

    @Test
    void testCreateMouvement_Sortie() {
        when(produitRepository.save(any())).thenReturn(produit);

        mouvementStockService.createMouvement(produit, 4, null, TypeMouvement.SORTIE, null);

        verify(produitRepository, times(1)).save(produit);
        assertEquals(6, produit.getStockActuel());
    }

    @Test
    void testCreateMouvement_SortieStockInsuffisant() {
        produit.setStockActuel(2);

        assertThrows(IllegalStateException.class,
                () -> mouvementStockService.createMouvement(produit, 5, null, TypeMouvement.SORTIE, null)
        );
    }

    @Test
    void testCreateMouvement_Ajustement() {
        when(produitRepository.save(any())).thenReturn(produit);

        mouvementStockService.createMouvement(produit, 0, null, TypeMouvement.AJUSTEMENT, null);

        verify(produitRepository).save(produit);
        assertEquals(10, produit.getStockActuel());
    }

    @Test
    void testCreateMouvement_AvecCommande() {
        Commande commande = new Commande();
        commande.setId(1L);

        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(produitRepository.save(any())).thenReturn(produit);

        mouvementStockService.createMouvement(produit, 3, null, TypeMouvement.ENTREE, 1L);

        verify(commandeRepository).findById(1L);
        verify(mouvementStockRepository).save(any());
    }

    @Test
    void testCreateMouvement_CommandeNotFound() {
        when(commandeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                mouvementStockService.createMouvement(produit, 3, null, TypeMouvement.ENTREE, 99L)
        );
    }

    
}
