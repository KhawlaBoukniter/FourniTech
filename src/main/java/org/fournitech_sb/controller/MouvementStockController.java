package org.fournitech_sb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.model.TypeMouvement;
import org.fournitech_sb.service.interfaces.MouvementStockServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mouvements-stock")
@RequiredArgsConstructor
@Tag(name = "Mouvements de stock", description = "API pour gérer les mouvements de stock")
public class MouvementStockController {

    private final MouvementStockServiceInterface mouvementStockService;

    @GetMapping
    @Operation(summary = "Lister tous les mouvements de stock", description = "Retourne la liste paginée de tous les mouvements de stock")
    public ResponseEntity<Page<MouvementStockDto>> getAllMouvements(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ) {
        return ResponseEntity.ok(mouvementStockService.getAllMouvements(page, size, sortBy, ascending));
    }

    @GetMapping("/produit/{produitId}")
    @Operation(summary = "Mouvements par produit", description = "Retourne les mouvements de stock pour un produit spécifique")
    public ResponseEntity<Page<MouvementStockDto>> getMouvementsByProduit(
            @PathVariable Long produitId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {

        return ResponseEntity.ok(mouvementStockService.getMouvementsByProduit(produitId, page, size, sortBy, ascending));
    }

    @GetMapping("/commande/{commandeId}")
    @Operation(summary = "Mouvements par commande", description = "Retourne les mouvements de stock liés à une commande spécifique")
    public ResponseEntity<Page<MouvementStockDto>> getMouvementsByCommande(
            @PathVariable Long commandeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {

        return ResponseEntity.ok(mouvementStockService.getMouvementsByCommande(commandeId, page, size, sortBy, ascending));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Mouvements par type", description = "Retourne les mouvements de stock filtrés par type (ENTREE ou SORTIE)")
    public ResponseEntity<Page<MouvementStockDto>> getMouvementsByType(
            @PathVariable TypeMouvement type,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {

        return ResponseEntity.ok(mouvementStockService.getMouvementsByType(type, page, size, sortBy, ascending));
    }
}
