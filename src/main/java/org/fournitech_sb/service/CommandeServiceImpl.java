package org.fournitech_sb.service;

import lombok.RequiredArgsConstructor;
import org.fournitech_sb.dto.CommandeDto;
import org.fournitech_sb.exception.ResourceNotFoundException;
import org.fournitech_sb.mapper.CommandeMapper;
import org.fournitech_sb.model.*;
import org.fournitech_sb.repository.CommandeRepositoryInterface;
import org.fournitech_sb.repository.FournisseurRepositoryInterface;
import org.fournitech_sb.repository.ProduitRepositoryInterface;
import org.fournitech_sb.service.interfaces.CommandeServiceInterface;
import org.fournitech_sb.service.interfaces.MouvementStockServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeServiceInterface {

    private final CommandeRepositoryInterface commandeRepository;
    private final FournisseurRepositoryInterface fournisseurRepository;
    private final ProduitRepositoryInterface produitRepository;
    private final MouvementStockServiceInterface mouvementStockService;
    private final CommandeMapper commandeMapper;

    @Override
    public CommandeDto createCommande(CommandeDto dto) {
        if (dto.getFournisseurId() == null) {
            throw new IllegalArgumentException("L'ID du fournisseur est requis.");
        }

        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé : " + dto.getFournisseurId()));

        Commande commande = commandeMapper.toEntity(dto);

        commande.setFournisseur(fournisseur);
        commande.setDateCommande(LocalDate.now());
        commande.setStatutCommande(StatutCommande.EN_ATTENTE);

        Double total = 0.0;
        for (ProduitCommande pc : commande.getProduitCommandes()) {
            Produit produit = produitRepository.findById(pc.getProduit().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé : " + pc.getProduit().getId()));

            pc.setPrixUnit(produit.getPrixUnit());
            pc.setCommande(commande);
            total += pc.getPrixUnit() * pc.getQuantite();
        }
        commande.setPrix(total);
        Commande saved = commandeRepository.save(commande);

        return commandeMapper.toDto(saved);
    }

    @Override
    public CommandeDto getCommandeById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec ID : " + id));
        return commandeMapper.toDto(commande);
    }

    @Override
    public Page<CommandeDto> getAllCommandes(Integer  page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return commandeRepository.findAll(pageable)
                .map(commandeMapper::toDto);
    }

    @Override
    public CommandeDto updateCommande(Long id, CommandeDto dto) {
        Commande existing = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée : " + id));

        if (dto.getFournisseurId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé : " + dto.getFournisseurId()));
            existing.setFournisseur(fournisseur);
        }

        if (dto.getProduitCommandes() != null) {
            existing.getProduitCommandes().clear();

            Commande temp = commandeMapper.toEntity(dto);
            Double total = 0.0;

            for (ProduitCommande pc : temp.getProduitCommandes()) {
                Produit produit = produitRepository.findById(pc.getProduit().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé : " + pc.getProduit().getId()));

                pc.setPrixUnit(produit.getPrixUnit());
                pc.setCommande(existing);
                existing.getProduitCommandes().add(pc);
                total += pc.getPrixUnit() * pc.getQuantite();
            }
            existing.setPrix(total);
        }

        if (dto.getStatutCommande() != null) {
            StatutCommande ancient = existing.getStatutCommande();
            existing.setStatutCommande(dto.getStatutCommande());

            if (ancient != StatutCommande.LIVREE && dto.getStatutCommande() == StatutCommande.LIVREE) {
                for (ProduitCommande pc : existing.getProduitCommandes()) {
                    Produit produit = produitRepository.findById(pc.getProduit().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé : " + pc.getProduit().getId()));

                    mouvementStockService.createMouvement(
                            produit,
                            pc.getQuantite(),
                            pc.getPrixUnit(),
                            TypeMouvement.SORTIE,
                            existing.getId()
                            );

                    produitRepository.save(produit);
                }
            }
        }

        Commande updated = commandeRepository.save(existing);

        return commandeMapper.toDto(updated);
    }

    @Override
    public void deleteCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec ID : " + id));

        if (commande.getStatutCommande() == StatutCommande.LIVREE) {
            throw new IllegalStateException("Impossible de supprimer une commande déjà livrée.");
        }

        commandeRepository.delete(commande);
    }

    @Override
    public CommandeDto annulerCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec ID : " + id));

        if (commande.getStatutCommande() == StatutCommande.LIVREE) {
            throw new IllegalStateException("Impossible d'annuler une commande déjà livrée.");
        }

        commande.setStatutCommande(StatutCommande.ANNULEE);
        Commande updated = commandeRepository.save(commande);
        return commandeMapper.toDto(updated);
    }
}
