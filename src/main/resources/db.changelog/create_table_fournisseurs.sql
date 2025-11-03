CREATE TABLE IF NOT EXISTS fournisseurs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) UNIQUE,
    nom VARCHAR(50),
    societe VARCHAR(50),
    adresse VARCHAR(100),
    contact VARCHAR(100),
    telephone VARCHAR(50),
    ville VARCHAR(50),
    ice VARCHAR(50) UNIQUE,
    createdAt TIMESTAMP
)