package org.fournitech_sb.repository;

import org.fournitech_sb.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepositoryInterface extends JpaRepository<Commande, Long> {
}
