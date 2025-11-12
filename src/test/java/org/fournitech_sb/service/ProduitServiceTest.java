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

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceTest {

    @Mock
    ProduitRepositoryInterface produitRepository;
    @Mock
    ProduitMapper produitMapper;

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

    

}
