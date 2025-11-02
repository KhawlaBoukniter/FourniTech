package org.fournitech_sb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.fournitech_sb.dto.ProduitDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fournitech_sb.service.interfaces.ProduitServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits du stock")
public class ProduitController {

    private final ProduitServiceInterface produitService;

    @Operation(summary = "Créer un produit", description = "Ajoute un nouveau produit et crée un mouvement de stock de type ENTRÉE automatiquement.")
    @PostMapping
    public ResponseEntity<ProduitDto> createProduit(@Valid @RequestBody ProduitDto produitDto) {
        ProduitDto created = produitService.createProduit(produitDto);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Obtenir un produit", description = "Récupère les informations d’un produit à partir de son ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDto> getProduitById(@PathVariable Long id) {
        ProduitDto produit = produitService.getProduitById(id);
        return ResponseEntity.ok(produit);
    }

    @Operation(summary = "Lister tous les produits", description = "Retourne une liste paginée de tous les produits.")
    @GetMapping
    public ResponseEntity<Page<ProduitDto>> getAllProduits(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(produitService.getAllProduits(pageable));
    }

    @Operation(summary = "Mettre à jour un produit", description = "Modifie les informations d’un produit existant.")
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDto> updateProduit(
            @PathVariable Long id,
            @Valid @RequestBody ProduitDto produitDto) {
        ProduitDto updated = produitService.updateProduit(id, produitDto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer un produit", description = "Supprime un produit par son ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
