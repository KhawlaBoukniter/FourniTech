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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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


}
