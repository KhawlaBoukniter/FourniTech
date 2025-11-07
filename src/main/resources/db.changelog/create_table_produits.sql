CREATE TABLE IF NOT EXISTS produits (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    description TEXT,
    prix_unit DECIMAL(10, 2),
    categorie VARCHAR(50),
    stock_actuel INT
)