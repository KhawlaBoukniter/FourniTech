package org.fournitech_sb.repository;


import org.fournitech_sb.model.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FournisseurRepositoryInterface extends JpaRepository<Fournisseur, Long> {
    Page<Fournisseur> findByEmailEndingWith(String email, Pageable pageable);

    Page<Fournisseur> findByNom(String nom, Pageable pageable);
}
