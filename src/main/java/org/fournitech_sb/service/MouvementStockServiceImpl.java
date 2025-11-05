package org.fournitech_sb.service;

import lombok.RequiredArgsConstructor;
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
import org.fournitech_sb.service.interfaces.MouvementStockServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MouvementStockServiceImpl implements MouvementStockServiceInterface {

    private final MouvementStockRepositoryInterface mouvementStockRepository;
    private final ProduitRepositoryInterface produitRepository;
    private final CommandeRepositoryInterface commandeRepository;
    private final MouvementStockMapper mouvementStockMapper;

//    @Override
//    public MouvementStockDto createMouvement(Produit produit, Integer quantite, TypeMouvement type, Long commandeId) {
//
//        MouvementStock mouvement = new MouvementStock();
//        mouvement.setProduit(produit);
//        mouvement.setQuantiteMouvement(quantite);
//        mouvement.setTypeMouvement(type);
//        mouvement.setDateMouvement(LocalDate.now());
//
//        if (commandeId != null) {
//            Commande commande = new Commande();
//            commande.setId(commandeId);
//            mouvement.setCommande(commande);
//        }
//
//        if (type == TypeMouvement.ENTREE) {
//            produit.setStockActuel(produit.getStockActuel() + quantite);
//            produit.setPrixUnit(calculerPrixUnitaireCUMP(produit, quantite, produit.getPrixUnit()));
//        } else if (type == TypeMouvement.SORTIE) {
//            produit.setStockActuel(produit.getStockActuel() - quantite);
//        }
//
//        produitRepository.save(produit);
//        MouvementStock saved = mouvementStockRepository.save(mouvement);
//
//        return mouvementStockMapper.toDto(saved);
//    }

    @Override
    public void createMouvement(Produit produit, Integer quantite, Double prixAchat, TypeMouvement typeMouvement, Long commandeId) {
        MouvementStock mouvement = new MouvementStock();
        mouvement.setProduit(produit);
        mouvement.setQuantiteMouvement(quantite);
        mouvement.setTypeMouvement(typeMouvement);
        mouvement.setDateMouvement(LocalDate.now());

        Commande commande = null;

        if (commandeId != null) {
            commande = commandeRepository.findById(commandeId).orElseThrow(() -> new ResourceNotFoundException("commande not found"));
        }

        mouvement.setCommande(commande);

        produit.setStockActuel(produit.getStockActuel() + quantite);

        if (typeMouvement.equals(TypeMouvement.ENTREE)) {
            produit.setStockActuel(produit.getStockActuel() + quantite);
            Double nouveauCump = calculerPrixUnitaireCUMP(produit, quantite, prixAchat);
            produit.setPrixUnit(nouveauCump);
        } else if (typeMouvement.equals(TypeMouvement.SORTIE)) {
            produit.setStockActuel(produit.getStockActuel() - quantite);

            if (produit.getStockActuel() <= 0) {
                throw new IllegalStateException("stock actuel insuffisant");
            }
        } else if (typeMouvement.equals(TypeMouvement.AJUSTEMENT)) {
            produit.setStockActuel(produit.getStockActuel() + quantite);
        }

        produitRepository.save(produit);
        mouvementStockRepository.save(mouvement);
    }

    @Override
    public Page<MouvementStockDto> getAllMouvements(Integer  page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return mouvementStockRepository.findAll(pageable)
                .map(mouvementStockMapper::toDto);
    }

    @Override
    public Page<MouvementStockDto> getMouvementsByProduit(Long produitId, Integer  page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return mouvementStockRepository.findByProduitId(produitId, pageable)
                .map(mouvementStockMapper::toDto);
    }

    @Override
    public Page<MouvementStockDto> getMouvementsByCommande(Long commandeId, Integer page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return mouvementStockRepository.findByCommandeId(commandeId, pageable)
                .map(mouvementStockMapper::toDto);
    }

    @Override
    public Page<MouvementStockDto> getMouvementsByType(TypeMouvement type,Integer page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return mouvementStockRepository.findAll(pageable)
                .map(mouvementStockMapper::toDto);
    }

    @Override
    public Double calculerPrixUnitaireCUMP(Produit produit, Integer quantiteEntree, Double prixEntree) {
        Integer stockActuel = produit.getStockActuel();
        Double prixActuel = produit.getPrixUnit() != null ? produit.getPrixUnit() : 0.0;

        if (stockActuel == 0) {
            return prixEntree;
        }

        return (prixActuel * stockActuel + prixEntree * quantiteEntree) / (stockActuel + quantiteEntree);
    }
}

