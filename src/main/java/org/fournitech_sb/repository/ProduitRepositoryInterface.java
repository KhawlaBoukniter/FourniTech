package org.fournitech_sb.repository;

import org.fournitech_sb.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepositoryInterface extends JpaRepository<Produit, Long> {
}
