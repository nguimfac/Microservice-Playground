Feature: Gestion du panier
  En tant qu'utilisateur
  Je veux gérer mon panier
  Afin de pouvoir ajouter des produits et valider mes achats

  Background:
    Given Given un panier avec l'id 1 existe en base

  Scenario Outline: Ajouter différents produits depuis le microservice Product
    Given le produit <productId> avec le nom "<productName>" et prix <price> existe
    When j'ajoute le produit <productId> au panier 1 avec la quantité <quantity>
    Then le produit est ajouté réellement au panier en base
    And le panier contient <expectedLines> ligne(s) en base de données
    And le total du panier en base est <expectedTotal>
    And la réponse HTTP a le statut 200
    And le service product externe a été appelé
    And les informations produit sont récupérées du microservice

    Examples:
      | productId | productName    | price  | quantity | expectedLines | expectedTotal |
      | 123       | iPhone 15      | 999.99 | 1        | 1             | 999.99        |
      | 124       | Samsung Galaxy | 799.99 | 2        | 1             | 1599.98       |
      | 125       | iPad Pro       | 1299.0 | 1        | 1             | 1299.0        |
      | 126       | MacBook Pro    | 2499.0 | 1        | 1             | 2499.0        |
      | 127       | AirPods Pro    | 249.99 | 3        | 1             | 749.97        |
