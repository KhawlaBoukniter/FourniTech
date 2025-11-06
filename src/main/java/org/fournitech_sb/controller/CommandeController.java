package org.fournitech_sb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fournitech_sb.dto.CommandeDto;
import org.fournitech_sb.service.interfaces.CommandeServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
@Tag(name = "Commandes", description = "API pour gérer les commandes")
public class CommandeController {

    private final CommandeServiceInterface commandeService;

    @PostMapping
    @Operation(summary = "Créer une commande", description = "Crée une nouvelle commande avec les informations fournies")
    public ResponseEntity<CommandeDto> createCommande(@Valid @RequestBody CommandeDto commandeDTO) {
        CommandeDto created = commandeService.createCommande(commandeDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une commande par ID", description = "Retourne une commande spécifique selon son ID")
    public ResponseEntity<CommandeDto> getCommandeById(@PathVariable Long id) {
        CommandeDto commande = commandeService.getCommandeById(id);
        return ResponseEntity.ok(commande);
    }

    @GetMapping
    @Operation(summary = "Lister les commandes", description = "Retourne une liste paginée des commandes, triable par champ et ordre")
    public ResponseEntity<Page<CommandeDto>> getAllCommandes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {

        return ResponseEntity.ok(commandeService.getAllCommandes(page, size, sortBy, ascending));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une commande", description = "Met à jour les informations d'une commande existante")
    public ResponseEntity<CommandeDto> updateCommande(
            @PathVariable Long id,
            @Valid @RequestBody CommandeDto commandeDTO) {
        CommandeDto updated = commandeService.updateCommande(id, commandeDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande", description = "Supprime une commande selon son ID")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/annuler/{id}")
    @Operation(summary = "Annuler une commande", description = "Annule une commande existante sans la supprimer")
    public ResponseEntity<CommandeDto> annulerCommande(@PathVariable Long id) {
        CommandeDto annulee = commandeService.annulerCommande(id);
        return ResponseEntity.ok(annulee);
    }

    @PatchMapping("/valider/{id}")
    @Operation(summary = "Valider une commande", description = "Valide une commande existante")
    public ResponseEntity<CommandeDto> validerCommande(@PathVariable Long id) {
        CommandeDto validee = commandeService.validerCommande(id);
        return ResponseEntity.ok(validee);
    }
}
