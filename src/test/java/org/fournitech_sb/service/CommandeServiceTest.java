package org.fournitech_sb.service;

import org.fournitech_sb.dto.CommandeDto;
import org.fournitech_sb.dto.ProduitCommandeDto;
import org.fournitech_sb.exception.ResourceNotFoundException;
import org.fournitech_sb.mapper.CommandeMapper;
import org.fournitech_sb.model.*;
import org.fournitech_sb.repository.CommandeRepositoryInterface;
import org.fournitech_sb.repository.FournisseurRepositoryInterface;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import org.fournitech_sb.service.interfaces.MouvementStockServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandeServiceTest {

    @Mock
    CommandeRepositoryInterface commandeRepository;
    @Mock
    FournisseurRepositoryInterface fournisseurRepository;
    @Mock
    ProduitRepositoryInterface produitRepository;
    @Mock
    MouvementStockServiceInterface mouvementStockService;
    @Mock
    CommandeMapper commandeMapper;

    @InjectMocks
    CommandeServiceImpl commandeService;


    private CommandeDto commandeDto;
    private Produit produit;
    private Fournisseur fournisseur;
    private Commande savedCommande;

    @BeforeEach
    void initData() {
        fournisseur = new Fournisseur();
        fournisseur.setId(1L);

        produit = new Produit();
        produit.setId(1L);
        produit.setNom("ProduitTest");

        commandeDto = new CommandeDto();
        commandeDto.setFournisseurId(1L);
        ProduitCommandeDto pcDto = new ProduitCommandeDto();
        pcDto.setProduitId(1L);
        pcDto.setQuantite(2);
        pcDto.setPrixUnit(10.0);
        commandeDto.setProduitCommandes(List.of(pcDto));

        savedCommande = new Commande();
        savedCommande.setId(100L);
        savedCommande.setProduitCommandes(new ArrayList<>());
        savedCommande.setStatutCommande(StatutCommande.EN_ATTENTE);
    }

    @Test
    void createCommandeTest() {
        Commande entity = new Commande();
        entity.setProduitCommandes(new ArrayList<>());
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));
        when(commandeMapper.toEntity(commandeDto)).thenReturn(entity);
        when(commandeRepository.save(entity)).thenReturn(savedCommande);
        when(commandeMapper.toDto(savedCommande)).thenReturn(commandeDto);

        CommandeDto result = commandeService.createCommande(commandeDto);
        assertNotNull(result);
        verify(commandeRepository, times(1)).save(entity);
    }

    @Test
    void validerCommandeTest() {
        ProduitCommande pc = new ProduitCommande();
        pc.setProduit(produit);
        pc.setQuantite(2);
        pc.setPrixUnit(10.0);
        savedCommande.getProduitCommandes().add(pc);

        when(commandeRepository.findById(100L)).thenReturn(Optional.of(savedCommande));
        when(produitRepository.findById(produit.getId())).thenReturn(Optional.of(produit));
        when(commandeRepository.save(savedCommande)).thenReturn(savedCommande);

        CommandeDto validatedDto = new CommandeDto();
        validatedDto.setStatutCommande(StatutCommande.LIVREE);
        when(commandeMapper.toDto(savedCommande)).thenReturn(validatedDto);

        CommandeDto validated = commandeService.validerCommande(100L);
        assertEquals(StatutCommande.LIVREE, validated.getStatutCommande());
        verify(mouvementStockService, times(1))
                .createMouvement(produit, 2, 10.0, TypeMouvement.SORTIE, 100L);
    }

    @Test
    void annulerCommandeTest() {
        when(commandeRepository.findById(100L)).thenReturn(Optional.of(savedCommande));
        when(commandeRepository.save(savedCommande)).thenReturn(savedCommande);

        CommandeDto annuleeDto = new CommandeDto();
        annuleeDto.setStatutCommande(StatutCommande.ANNULEE);
        when(commandeMapper.toDto(savedCommande)).thenReturn(annuleeDto);

        CommandeDto annulee = commandeService.annulerCommande(100L);
        assertEquals(StatutCommande.ANNULEE, annulee.getStatutCommande());
    }

    @Test
    void annulerCommandeAlreadyLivreeThrowsTest() {
        savedCommande.setStatutCommande(StatutCommande.LIVREE);
        when(commandeRepository.findById(100L)).thenReturn(Optional.of(savedCommande));

        Exception ex = assertThrows(IllegalStateException.class, () -> commandeService.annulerCommande(100L));
        assertTrue(ex.getMessage().contains("Impossible d'annuler"));
    }

    @Test
    void deleteCommandeTest() {
        savedCommande.setStatutCommande(StatutCommande.EN_ATTENTE);
        when(commandeRepository.findById(100L)).thenReturn(Optional.of(savedCommande));

        commandeService.deleteCommande(100L);
        verify(commandeRepository, times(1)).delete(savedCommande);
    }

    
}
