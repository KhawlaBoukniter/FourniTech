package org.fournitech_sb.repository;

import org.fournitech_sb.model.ProduitCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitCommandeRepositoryInterface extends JpaRepository<ProduitCommande, Long> {
}
