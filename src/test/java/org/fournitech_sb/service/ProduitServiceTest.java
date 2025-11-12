package org.fournitech_sb.service;

import org.fournitech_sb.dto.ProduitDto;
import org.fournitech_sb.mapper.ProduitMapper;
import org.fournitech_sb.model.Produit;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceTest {

    @Mock
    ProduitRepositoryInterface produitRepository;
    @Mock
    ProduitMapper produitMapper;

    @Mock
    MouvementStockServiceImpl mouvementStockService;

    @InjectMocks
    ProduitServiceImpl produitService;

    @Test
    public void addProduitTest(){
        ProduitDto produitDto = new ProduitDto();
        produitDto.setId(1L);
        produitDto.setNom("Test");

        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Test");

        when(produitMapper.toEntity(produitDto)).thenReturn(produit);
        when(produitRepository.save(produit)).thenReturn(produit);
        when(produitMapper.toDto(produit)).thenReturn(produitDto);

        ProduitDto rslt = produitService.createProduit(produitDto);

        assertNotNull(rslt);
        assertEquals(produit.getId(), rslt.getId());
        verify(produitRepository).save(produit);
    }

    @Test
    public void updateProduitTest() {
        Long produitId = 1L;

        Produit existingProduit = new Produit();
        existingProduit.setId(produitId);
        existingProduit.setNom("Test");
        existingProduit.setStockActuel(10);
        existingProduit.setPrixUnit(100.0);

        ProduitDto updatedDto = new ProduitDto();
        updatedDto.setId(produitId);
        updatedDto.setNom("updatedTest");
        updatedDto.setStockActuel(15);
        updatedDto.setPrixUnit(100.0);

        Produit updatedProduit = new Produit();
        updatedProduit.setId(produitId);
        updatedProduit.setNom("updatedTest");
        updatedProduit.setStockActuel(15);
        updatedProduit.setPrixUnit(100.0);

        when(produitRepository.findById(produitId)).thenReturn(Optional.of(existingProduit));
        when(produitRepository.save(existingProduit)).thenReturn(updatedProduit);
        when(produitMapper.toDto(updatedProduit)).thenReturn(updatedDto);

        ProduitDto result = produitService.updateProduit(produitId, updatedDto);

        assertNotNull(result);
        assertEquals("updatedTest", result.getNom());
        assertEquals(15, result.getStockActuel());
        verify(produitRepository).findById(produitId);
        verify(produitRepository).save(existingProduit);
        verify(produitMapper).toDto(updatedProduit);
    }

    @Test
    public void findAllTest() {
        Produit produit = new Produit();
        produit.setId(1L);

        ProduitDto produitDto = new ProduitDto();
        produitDto.setId(1L);

        Page<Produit> pageMock = new PageImpl<>(List.of(produit));
        when(produitRepository.findAll(any(Pageable.class))).thenReturn(pageMock);
        when(produitMapper.toDto(produit)).thenReturn(produitDto);

        Pageable pageable = PageRequest.of(0, 10);

        Page<ProduitDto> produits = produitService.getAllProduits(pageable);

        assertEquals(1, produits.getContent().size());
        assertEquals(1L, produits.getContent().get(0).getId());
        verify(produitRepository).findAll(any(Pageable.class));
    }

    @Test
    public void findByIdTest() {
        Long produitId = 1L;
        Produit produit = new Produit();
        produit.setId(produitId);
        produit.setNom("Test");

        ProduitDto produitDto = new ProduitDto();
        produitDto.setId(produitId);
        produitDto.setNom("Test");

        when(produitRepository.findById(produitId)).thenReturn(Optional.of(produit));
        when(produitMapper.toDto(produit)).thenReturn(produitDto);

        ProduitDto result = produitService.getProduitById(produitId);

        assertNotNull(result);
        assertEquals(produitId, result.getId());
        assertEquals("Test", result.getNom());
        verify(produitRepository).findById(produitId);
    }



}
