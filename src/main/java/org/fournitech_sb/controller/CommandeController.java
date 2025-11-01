package org.fournitech_sb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fournitech_sb.dto.CommandeDto;
import org.fournitech_sb.service.interfaces.CommandeServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeServiceInterface commandeService;

    @PostMapping
    public ResponseEntity<CommandeDto> createCommande(@Valid @RequestBody CommandeDto commandeDTO) {
        CommandeDto created = commandeService.createCommande(commandeDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDto> getCommandeById(@PathVariable Long id) {
        CommandeDto commande = commandeService.getCommandeById(id);
        return ResponseEntity.ok(commande);
    }

    @GetMapping
    public ResponseEntity<Page<CommandeDto>> getAllCommandes(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending) {

        return ResponseEntity.ok(commandeService.getAllCommandes(page, size, sortBy, ascending));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeDto> updateCommande(
            @PathVariable Long id,
            @Valid @RequestBody CommandeDto commandeDTO) {
        CommandeDto updated = commandeService.updateCommande(id, commandeDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/annuler/{id}")
    public ResponseEntity<CommandeDto> annulerCommande(@PathVariable Long id) {
        CommandeDto annulee = commandeService.annulerCommande(id);
        return ResponseEntity.ok(annulee);
    }
}
