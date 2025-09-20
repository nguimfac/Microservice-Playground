Feature: Gestion du panier
  En tant qu'utilisateur
  Je veux gérer mon panier
  Afin de pouvoir ajouter des produits et valider mes achats

  Background:
    Given le service de panier est disponible

    Scenario Outline: Ajouter différents produits
      Given on a un panier avec l'id <cartId>  et un produit d'id <productId> qui existe
      When  j'ajoute <quantite> produit d'id <productId> au panier d'id <cartId>
      Then  Le produit est ajoute au panier
      And   le panier contient <nombrerOfitem>
      And   le panier contient le produit d'id <productId> et une quantite de <quantite>
      And   la reponse http est <httpResponseStatus>

      Examples:
        | cartId | productId | productName    | quantity | expectedItems | expectedStatus |
        | 1001   | 123       | iPhone 15      | 1        | 1             | 200            |
        | 1001   | 124       | Samsung Galaxy | 2        | 2             | 200            |
        | 1002   | 125       | iPad Pro       | 3        | 1             | 200            |
        | 1003   | 126       | MacBook Pro    | 1        | 1             | 200            |