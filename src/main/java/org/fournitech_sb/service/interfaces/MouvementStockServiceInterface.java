package org.fournitech_sb.service.interfaces;

import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.model.Produit;
import org.fournitech_sb.model.TypeMouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MouvementStockServiceInterface {
//    MouvementStockDto createMouvement(Produit produit, Integer quantite, TypeMouvement type, Long commandeId);
    void createMouvement(Produit produit, Integer quantite, Double prixAchat, TypeMouvement typeMouvement, Long commandeId);

    Page<MouvementStockDto> getAllMouvements(Integer page, Integer size, String sortBy, Boolean ascending);

    Page<MouvementStockDto> getMouvementsByProduit(Long produitId, Integer page, Integer size, String sortBy, Boolean ascending);

    Page<MouvementStockDto> getMouvementsByCommande(Long commandeId, Integer page, Integer size, String sortBy, Boolean ascending);

    Page<MouvementStockDto> getMouvementsByType(TypeMouvement type, Integer page, Integer size, String sortBy, Boolean ascending);

    Double calculerPrixUnitaireCUMP(Produit produit, Integer quantiteEntree, Double prixEntree);
}
