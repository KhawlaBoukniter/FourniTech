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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FournisseurServiceTest {

    @Mock
    FournisseurRepositoryInterface fournisseurRepository;

    @InjectMocks
    FournisseurServiceImpl fournisseurService;

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

}
