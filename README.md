# Fournitech SB - Gestion d’approvisionnement

## Table des matières

* [Description](#description)
* [Technologies](#technologies)
* [Installation](#installation)
* [Configuration](#configuration)
* [Structure du projet](#structure-du-projet)
* [Fonctionnalités](#fonctionnalités)
* [API Documentation](#api-documentation)
* [Base de données](#base-de-données)
* [Contributions](#contributions)
* [Licence](#licence)

---

## Description

Fournitech SB est une application backend pour la **gestion des produits, commandes et mouvements de stock**.
Elle permet de gérer :

* Les fournisseurs et leurs informations.
* Les produits avec leur stock actuel et prix unitaire.
* Les commandes (création, mise à jour, annulation, livraison).
* Les mouvements de stock (entrée, sortie, ajustement) avec calcul du prix unitaire CUMP.

Le projet utilise **Spring Boot**, **Spring Data JPA**, **MapStruct**, **Liquibase**, et est documenté avec **Swagger/OpenAPI**.

---

## Technologies

* Java 17
* Spring Boot 3
* Spring Data JPA
* Hibernate / MySQL 8
* MapStruct
* Liquibase
* Springdoc OpenAPI (Swagger)
* Maven
* Git

---

## Installation

1. **Cloner le projet :**

```bash
git clone https://github.com/fournitech/fournitech-sb.git
cd fournitech-sb
```

2. **Configurer la base de données MySQL :**

* Créer la base :

```sql
CREATE DATABASE fourniTech;
```

* Configurer `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fourniTech
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

3. **Exécuter les migrations Liquibase :**

```bash
mvn liquibase:update
```

4. **Lancer l’application :**

```bash
mvn spring-boot:run
```

---

## Configuration

Le projet inclut un fichier de configuration pour Swagger/OpenAPI (`OpenApiConfig.java`) qui permet de générer automatiquement la documentation des endpoints sur :

```
http://localhost:8080/swagger-ui.html
```

---

## Structure du projet

```
src/main/java/org/fournitech_sb/
├─ config/          # Configurations (Swagger, etc.)
├─ controller/      # Controllers REST
├─ dto/             # DTO pour les échanges API
├─ exception/       # Exceptions personnalisées
├─ mapper/          # MapStruct mappers
├─ model/           # Entités JPA
├─ repository/      # Interfaces Spring Data JPA
├─ service/         # Logique métier
└─ advice/          # Gestion globale des exceptions
```

---

## Fonctionnalités

### Fournisseurs

* Ajouter, modifier, supprimer un fournisseur.
* Recherche par nom ou email.
* Pagination et tri.

### Produits

* Gestion du stock actuel.
* Calcul du prix unitaire CUMP lors des entrées en stock.

### Commandes

* Créer une commande avec plusieurs produits.
* Mise à jour et annulation des commandes.
* Changement de statut (EN_ATTENTE, VALIDEE, LIVREE, ANNULEE).

### Mouvements de stock

* Entrée, sortie et ajustement.
* Historique filtrable par produit, commande ou type de mouvement.
* Mise à jour automatique du stock actuel et du prix unitaire.

---

## API Documentation

* Base URL : `http://localhost:8080/api`
* Swagger UI : `http://localhost:8080/swagger-ui.html`

Endpoints principaux :

| Ressource                             | Méthode | Description                    |
| ------------------------------------- | ------- | ------------------------------ |
| `/api/commandes`                      | GET     | Récupérer toutes les commandes |
| `/api/commandes/{id}`                 | GET     | Récupérer une commande par ID  |
| `/api/commandes`                      | POST    | Créer une commande             |
| `/api/commandes/{id}`                 | PUT     | Mettre à jour une commande     |
| `/api/commandes/{id}`                 | DELETE  | Supprimer une commande         |
| `/api/commandes/annuler/{id}`         | PATCH   | Annuler une commande           |
| `/api/mouvements-stock`               | GET     | Récupérer tous les mouvements  |
| `/api/mouvements-stock/produit/{id}`  | GET     | Mouvements par produit         |
| `/api/mouvements-stock/commande/{id}` | GET     | Mouvements par commande        |
| `/api/mouvements-stock/type/{type}`   | GET     | Mouvements par type            |

---

## Base de données

Tables principales :

* `produits`
* `fournisseurs`
* `commandes`
* `produit_commande`
* `mouvements_stock`

Migrations gérées via **Liquibase** avec `db.changelog/app-changelog.xml`.

---

## Tests

Le projet inclut **des tests unitaires et d’intégration** couvrant les services et les endpoints REST.

### Tests Unitaires

* **ProduitServiceTest** : création, mise à jour, suppression et recherche de produits.
* **MouvementStockServiceTest** : création de mouvements (ENTREE, SORTIE, AJUSTEMENT), calcul du CUMP, vérification des exceptions.
* **FournisseurServiceTest** : création, mise à jour, recherche, suppression.
* **CommandeServiceTest** : création, validation, annulation et suppression des commandes, vérification des exceptions.

### Tests d’Intégration

* **ProduitIntegrationTest** : test de création, récupération, listing et suppression via l’API.
* **MouvementStockIntegrationTest** : test de listing global et filtré par produit, commande et type.
* **CommandeIntegrationTest** : création, validation, annulation et suppression de commandes via l’API.

Tous les tests utilisent **Testcontainers** pour lancer une base MySQL isolée.

Commandes pour exécuter les tests :

```bash
mvn test jacoco:report
```

---

## Contributions

Les contributions sont les bienvenues !
Pour proposer des modifications :

1. Fork le projet
2. Créer une branche : `git checkout -b feature/nom-de-la-fonctionnalite`
3. Commit tes modifications : `git commit -m "Description de la fonctionnalité"`
4. Push : `git push origin feature/nom-de-la-fonctionnalite`
5. Ouvrir un Pull Request

---

