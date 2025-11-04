package org.fournitech_sb.service;

import jakarta.persistence.EntityNotFoundException;
import org.fournitech_sb.exception.ResourceNotFoundException;
import org.fournitech_sb.model.Fournisseur;
import org.fournitech_sb.repository.FournisseurRepositoryInterface;
import org.fournitech_sb.service.interfaces.FournisseurServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FournisseurServiceImpl implements FournisseurServiceInterface {
    private final FournisseurRepositoryInterface fournisseurRepository;

    public FournisseurServiceImpl(FournisseurRepositoryInterface fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    public void addFournisseur(Fournisseur f) {
        fournisseurRepository.save(f);
    }

    public void deleteFournisseur(Fournisseur f) {
        fournisseurRepository.delete(f);
    }

    public void updateFournisseur(Fournisseur f) {
        fournisseurRepository.save(f);
    }

    public Fournisseur findFournisseurById(Long id) {
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return fournisseur.orElseThrow(
                () -> new ResourceNotFoundException("Fournisseur non trouvé avec ID : " + id)
        );
    }

    public Page<Fournisseur> findAllFournisseur(Integer  page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return fournisseurRepository.findAll(pageable);
    }


    public Page<Fournisseur> findFournisseurByNom(String nom, Integer page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return fournisseurRepository.findByNom(nom, pageable);
    }

    public Page<Fournisseur> findFournisseurByNomEndingWith(String ending, Integer page, Integer size, String sortBy, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return fournisseurRepository.findByEmailEndingWith(ending, pageable);
    }

    public Long countFournisseurs() {
        return fournisseurRepository.count();
    }
}
