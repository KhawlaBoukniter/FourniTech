package org.fournitech_sb.controller;

import jakarta.validation.Valid;
import org.fournitech_sb.dto.FournisseurDto;
import org.fournitech_sb.mapper.FournisseurMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.fournitech_sb.model.Fournisseur;
import org.fournitech_sb.service.interfaces.FournisseurServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurServiceInterface FournisseurServiceInterface;
    private final FournisseurMapper fournisseurMapper;

    @Autowired
    public FournisseurController(FournisseurServiceInterface FournisseurServiceInterface,  FournisseurMapper fournisseurMapper) {
        this.FournisseurServiceInterface = FournisseurServiceInterface;
        this.fournisseurMapper = fournisseurMapper;
    }

    @GetMapping
    public ResponseEntity<Page<FournisseurDto>> getAllFournisseurs(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending) {
        Page<FournisseurDto> fournisseurs = FournisseurServiceInterface.findAllFournisseur(page, size, sortBy, ascending).map(fournisseurMapper::toDto);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDto> getFournisseurById(@PathVariable("id") Long id) {
        Fournisseur fournisseur = FournisseurServiceInterface.findFournisseurById(id);
        return ResponseEntity.ok(fournisseurMapper.toDto(fournisseur));
    }

    @PostMapping
    public ResponseEntity<FournisseurDto> createFournisseur(@Valid @RequestBody FournisseurDto fournisseurDto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);
        FournisseurServiceInterface.addFournisseur(fournisseur);
        return ResponseEntity.ok(fournisseurMapper.toDto(fournisseur));
    }


    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDto> updateFournisseur(@PathVariable("id") Long id, @Valid @RequestBody FournisseurDto updatedFournisseurDto) {
        Fournisseur fournisseur = FournisseurServiceInterface.findFournisseurById(id);
            fournisseur.setNom(updatedFournisseurDto.getNom());
            fournisseur.setSociete(updatedFournisseurDto.getSociete());
            fournisseur.setAdresse(updatedFournisseurDto.getAdresse());
            fournisseur.setContact(updatedFournisseurDto.getContact());
            fournisseur.setEmail(updatedFournisseurDto.getEmail());
            fournisseur.setTelephone(updatedFournisseurDto.getTelephone());
            fournisseur.setVille(updatedFournisseurDto.getVille());
            fournisseur.setIce(updatedFournisseurDto.getIce());
            FournisseurServiceInterface.updateFournisseur(fournisseur);
            return ResponseEntity.ok(fournisseurMapper.toDto(fournisseur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable("id") Long id) {
        Fournisseur fournisseur = FournisseurServiceInterface.findFournisseurById(id);
        if (fournisseur != null) {
            FournisseurServiceInterface.deleteFournisseur(fournisseur);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<Page<FournisseurDto>> findFournisseurByNom(@RequestParam("nom") String nom, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending) {
        Page<FournisseurDto> fournisseurs = FournisseurServiceInterface.findFournisseurByNom(nom, page, size, sortBy, ascending).map(fournisseurMapper::toDto);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/byEmailEndingWith")
    public ResponseEntity<Page<FournisseurDto>> findByEmailEndingWith(@RequestParam("ending") String ending, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") Boolean ascending) {
        Page<FournisseurDto> fournisseurs = FournisseurServiceInterface.findFournisseurByNomEndingWith(ending, page, size, sortBy, ascending).map(fournisseurMapper::toDto);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countFournisseurs() {
        Long count = FournisseurServiceInterface.countFournisseurs();
        return ResponseEntity.ok(count);
    }

}
