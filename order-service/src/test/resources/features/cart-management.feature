Feature: Gestion du panier en tant qu'utilisateur je veux gerer mon panier afin de pouvoir ajouter des produits et valider mes achats

  Background:
    Given un panier avec l'id 1 existe et appartient au user d'id 1

    Scenario: Ajouter plusieur produit dans le panier
      Given Les produits suivant existent
                  | productCode | productName    | price  | quantity| productId|
                  | 123         | iPhone 15        | 999.99 |  4    |   1      |
                  | 124         | Samsung Galaxy   | 799.99 |  6    |   2      |
                  | 125         | iPad Pro         | 1299.0 |  6    |   3      |

      When j'ajoute les produits suivants au panier 1
                  | productId | quantity |
                  | 1       | 1          |
                  | 2       | 2          |
                  | 3       | 1          |
      Then le panier d'id 1 contient 3 ligne(s) en base de données
      And  le total du panier d'id 1 en base est 3898.97
      And  les réponses HTTP ont le statut 200
      And  le service product externe a été appelé