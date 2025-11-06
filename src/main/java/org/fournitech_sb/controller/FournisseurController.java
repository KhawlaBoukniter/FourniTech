package org.fournitech_sb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fournitech_sb.dto.FournisseurDto;
import org.fournitech_sb.mapper.FournisseurMapper;
import org.fournitech_sb.model.Fournisseur;
import org.fournitech_sb.service.interfaces.FournisseurServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fournisseurs")
@Tag(name = "Fournisseurs", description = "API pour gérer les fournisseurs")
public class FournisseurController {

    private final FournisseurServiceInterface fournisseurService;
    private final FournisseurMapper fournisseurMapper;

    @Autowired
    public FournisseurController(FournisseurServiceInterface fournisseurService, FournisseurMapper fournisseurMapper) {
        this.fournisseurService = fournisseurService;
        this.fournisseurMapper = fournisseurMapper;
    }

    @GetMapping
    @Operation(summary = "Lister les fournisseurs", description = "Retourne la liste paginée de tous les fournisseurs")
    public ResponseEntity<Page<FournisseurDto>> getAllFournisseurs(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        Page<FournisseurDto> fournisseurs = fournisseurService.findAllFournisseur(page, size, sortBy, ascending)
                .map(fournisseurMapper::toDto);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un fournisseur par ID", description = "Retourne un fournisseur selon son ID")
    public ResponseEntity<FournisseurDto> getFournisseurById(@PathVariable("id") Long id) {
        Fournisseur fournisseur = fournisseurService.findFournisseurById(id);
        return ResponseEntity.ok(fournisseurMapper.toDto(fournisseur));
    }

    @PostMapping
    @Operation(summary = "Créer un fournisseur", description = "Crée un nouveau fournisseur avec les informations fournies")
    public ResponseEntity<FournisseurDto> createFournisseur(@Valid @RequestBody FournisseurDto fournisseurDto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);
        fournisseurService.addFournisseur(fournisseur);
        return ResponseEntity.ok(fournisseurMapper.toDto(fournisseur));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un fournisseur", description = "Met à jour les informations d'un fournisseur existant")
    public ResponseEntity<FournisseurDto> updateFournisseur(
            @PathVariable("id") Long id,
            @Valid @RequestBody FournisseurDto updatedFournisseurDto) {
        Fournisseur fournisseur = fournisseurService.findFournisseurById(id);
        fournisseur.setNom(updatedFournisseurDto.getNom());
        fournisseur.setSociete(updatedFournisseurDto.getSociete());
        fournisseur.setAdresse(updatedFournisseurDto.getAdresse());
        fournisseur.setContact(updatedFournisseurDto.getContact());
        fournisseur.setEmail(updatedFournisseurDto.getEmail());
        fournisseur.setTelephone(updatedFournisseurDto.getTelephone());
        fournisseur.setVille(updatedFournisseurDto.getVille());
        fournisseur.setIce(updatedFournisseurDto.getIce());
        fournisseurService.updateFournisseur(fournisseur);
        return ResponseEntity.ok(fournisseurMapper.toDto(fournisseur));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un fournisseur", description = "Supprime un fournisseur selon son ID")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable("id") Long id) {
        Fournisseur fournisseur = fournisseurService.findFournisseurById(id);
        if (fournisseur != null) {
            fournisseurService.deleteFournisseur(fournisseur);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byName")
    @Operation(summary = "Rechercher des fournisseurs par nom", description = "Retourne les fournisseurs dont le nom correspond à la recherche")
    public ResponseEntity<Page<FournisseurDto>> findFournisseurByNom(
            @RequestParam("nom") String nom,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        Page<FournisseurDto> fournisseurs = fournisseurService.findFournisseurByNom(nom, page, size, sortBy, ascending)
                .map(fournisseurMapper::toDto);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/byEmailEndingWith")
    @Operation(summary = "Rechercher des fournisseurs par fin d'email", description = "Retourne les fournisseurs dont l'email se termine par la chaîne fournie")
    public ResponseEntity<Page<FournisseurDto>> findByEmailEndingWith(
            @RequestParam("ending") String ending,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        Page<FournisseurDto> fournisseurs = fournisseurService.findFournisseurByNomEndingWith(ending, page, size, sortBy, ascending)
                .map(fournisseurMapper::toDto);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/count")
    @Operation(summary = "Compter les fournisseurs", description = "Retourne le nombre total de fournisseurs")
    public ResponseEntity<Long> countFournisseurs() {
        Long count = fournisseurService.countFournisseurs();
        return ResponseEntity.ok(count);
    }
}
