package org.fournitech_sb.controller;

import lombok.RequiredArgsConstructor;
import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.model.TypeMouvement;
import org.fournitech_sb.service.interfaces.MouvementStockServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mouvements-stock")
@RequiredArgsConstructor
public class MouvementStockController {

    private final MouvementStockServiceInterface mouvementStockService;

    @GetMapping
    public ResponseEntity<Page<MouvementStockDto>> getAllMouvements(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending
    ) {
        return ResponseEntity.ok(mouvementStockService.getAllMouvements(page, size, sortBy, ascending));
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<Page<MouvementStockDto>> getMouvementsByProduit(
            @PathVariable Long produitId,
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending) {

        return ResponseEntity.ok(mouvementStockService.getMouvementsByProduit(produitId, page, size, sortBy, ascending));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<Page<MouvementStockDto>> getMouvementsByCommande(
            @PathVariable Long commandeId,
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending    ) {

        return ResponseEntity.ok(mouvementStockService.getMouvementsByCommande(commandeId, page, size, sortBy, ascending));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<MouvementStockDto>> getMouvementsByType(
            @PathVariable TypeMouvement type,
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending    ) {

        return ResponseEntity.ok(mouvementStockService.getMouvementsByType(type, page, size, sortBy, ascending));
    }
}

