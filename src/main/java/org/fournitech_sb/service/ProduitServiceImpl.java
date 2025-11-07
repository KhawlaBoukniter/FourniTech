package org.fournitech_sb.service;

import org.fournitech_sb.dto.ProduitDto;
import org.fournitech_sb.model.Produit;
import org.fournitech_sb.exception.ResourceNotFoundException;
import org.fournitech_sb.mapper.ProduitMapper;
import org.fournitech_sb.model.TypeMouvement;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.fournitech_sb.service.interfaces.MouvementStockServiceInterface;
import org.fournitech_sb.service.interfaces.ProduitServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitServiceInterface {

    private final ProduitRepositoryInterface produitRepository;
    private final MouvementStockServiceInterface mouvementStockService;
    private final ProduitMapper produitMapper;

    @Override
    public ProduitDto createProduit(ProduitDto produitDto) {
        Produit produit = produitMapper.toEntity(produitDto);
        produit.setStockActuel(produitDto.getStockActuel() != null ? produitDto.getStockActuel() : 0);

        Produit savedProduit = produitRepository.save(produit);

        if (savedProduit.getStockActuel() != null && savedProduit.getStockActuel() > 0) {
            mouvementStockService.createMouvement(
                    savedProduit,
                    produitDto.getStockActuel(),
                    produitDto.getPrixUnit(),
                    TypeMouvement.ENTREE,
                    null
            );
        }

        return produitMapper.toDto(savedProduit);
    }

    @Override
    public ProduitDto getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec ID : " + id));
        return produitMapper.toDto(produit);
    }

    @Override
    public Page<ProduitDto> getAllProduits(Pageable pageable) {
        return produitRepository.findAll(pageable)
                .map(produitMapper::toDto);
    }

    @Override
    public ProduitDto updateProduit(Long id, ProduitDto produitDto) {
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec ID : " + id));

        existingProduit.setNom(produitDto.getNom());
        existingProduit.setDescription(produitDto.getDescription());
        existingProduit.setPrixUnit(produitDto.getPrixUnit());
        existingProduit.setCategorie(produitDto.getCategorie());
        existingProduit.setStockActuel(produitDto.getStockActuel());

        Produit updated = produitRepository.save(existingProduit);
        return produitMapper.toDto(updated);
    }

    @Override
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec ID : " + id));
        produitRepository.delete(produit);
    }
}
