CREATE TABLE IF NOT EXISTS mouvements_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_mouvement DATE DEFAULT CURRENT_DATE,
    quantite_mouvement INT,
    produit_id BIGINT,
    commande_id BIGINT,
    type_mouvement ENUM('ENTREE', 'SORTIE', 'AJUSTEMENT') NOT NULL,
    FOREIGN KEY (produit_id) REFERENCES produits(id),
    FOREIGN KEY (commande_id) REFERENCES commandes(id)
)