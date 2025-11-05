package org.fournitech_sb.repository;

import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.model.MouvementStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MouvementStockRepositoryInterface extends JpaRepository<MouvementStock, Long> {
    Page<MouvementStock> findByProduitId(Long produitId, Pageable pageable);
    Page<MouvementStock> findByCommandeId(Long commandeId, Pageable pageable);
}
