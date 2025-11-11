package org.fournitech_sb.service.interfaces;


import org.fournitech_sb.model.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FournisseurServiceInterface {
    Fournisseur addFournisseur(Fournisseur f);
    void deleteFournisseur(Fournisseur f);
    Fournisseur updateFournisseur(Fournisseur f);
    Fournisseur findFournisseurById(Long id);
    Page<Fournisseur> findAllFournisseur(Integer page, Integer size, String sortBy, Boolean ascending);
    Page<Fournisseur> findFournisseurByNom(String nom, Integer page, Integer size, String sortBy, Boolean ascending);
    Page<Fournisseur> findFournisseurByNomEndingWith(String ending, Integer page, Integer size, String sortBy, Boolean ascending);
    Long countFournisseurs();
}
