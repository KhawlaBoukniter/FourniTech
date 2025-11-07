CREATE TABLE IF NOT EXISTS commandes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fournisseur_id BIGINT,
    prix DOUBLE,
    statut_commande ENUM('EN_ATTENTE', 'VALIDEE', 'LIVREE', 'ANNULEE') DEFAULT 'EN_ATTENTE' NOT NULL,
    date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fournisseur_id) REFERENCES fournisseurs(id)
)