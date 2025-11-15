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

    
}
