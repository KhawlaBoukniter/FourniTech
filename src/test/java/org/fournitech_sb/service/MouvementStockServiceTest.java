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

    
}
