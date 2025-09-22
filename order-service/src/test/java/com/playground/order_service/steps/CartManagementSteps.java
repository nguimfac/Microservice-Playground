package com.playground.order_service.steps;

import com.playground.constant.CartStatusEnum;
import com.playground.dto.response.ProductResponse;
import com.playground.order_service.dao.CartItemRepository;
import com.playground.order_service.dao.CartRepository;
import com.playground.order_service.model.cart.Cart;
import com.playground.order_service.model.cart.CartItem;
import com.playground.order_service.service.facade.feignClient.InventoryClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
//un roolback apres chaque test
public class CartManagementSteps {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @MockitoBean
    private MockMvc mockMvc;

    private int lastHttpStatus;


    @Given("Given un panier avec l'id {int} existe en base")
    public void givenUnPanierAvecLIdExisteEnBase(int cartId) {
        Cart existingCart = cartRepository.findById((long) cartId).orElse(new Cart(new ArrayList<>() ,CartStatusEnum.INIT ));
        cartRepository.save(existingCart);
        assertTrue(cartRepository.findById((long) cartId).isPresent());
    }




    @Given("le produit {int} avec le nom {string} et prix {double} existe")
    public void leProduitExiste(Integer productId, String productName, Double price) {
        ProductResponse response = new ProductResponse(
                productId.longValue(),
                productName,
                0, // stock
                BigDecimal.valueOf(price),
                productName
        );
        Mockito.when(inventoryClient.getProductById(productId.longValue()))
                .thenReturn(response);
    }

    @When("j'ajoute le produit {int} au panier {int} avec la quantité {int}")
    public void ajouterProduitAuPanier(int productId, int cartId, int quantity) throws Exception {
        String json = "{ \"productId\": " + productId + ", \"quantity\": " + quantity + " }";

        lastHttpStatus = mockMvc.perform(post("/api/order/cart" + cartId + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getStatus();
    }

    @Then("le produit est ajouté réellement au panier en base")
    public void leProduitEstAjoutéRéellementAuPanierEnBase() {
        List<CartItem> items = cartItemRepository.findAll();
        assertFalse(items.isEmpty(), "Le panier devrait contenir au moins un produit");
    }

    @And("le panier contient {int} ligne\\(s) en base de données")
    public void lePanierContientExpectedLinesLigneSEnBaseDeDonnées(int expectedLines) {
        List<CartItem> items = cartItemRepository.findAll();
        assertEquals(expectedLines, items.size(), "Le nombre de lignes dans le panier est incorrect");
    }

    @And("le total du panier en base est {double}")
    public void verifierTotalPanier(double expectedTotal) {
        List<CartItem> items = cartItemRepository.findAll();
        double total = items.stream()
                .mapToDouble(i -> {
                    ProductResponse product = inventoryClient.getProductById(i.getProductId());
                    return product.price().doubleValue() * i.getQuantity();
                })
                .sum();
        assertEquals(expectedTotal, total, 0.01, "Le total du panier est incorrect");
    }

    @And("la réponse HTTP a le statut {int}")
    public void laRéponseHTTPALeStatut(int status) {
        assertEquals(status, lastHttpStatus, "Le statut HTTP reçu est incorrect");
    }

    @And("le service product externe a été appelé")
    public void leServiceProductExterneAÉtéAppelé() {
        Mockito.verify(inventoryClient, Mockito.atLeastOnce()).getProductById(Mockito.anyLong());
    }

    @And("les informations produit sont récupérées du microservice")
    public void lesInformationsProduitSontRécupéréesDuMicroservice() {
        List<CartItem> items = cartItemRepository.findAll();
        items.forEach(item -> {
            ProductResponse product = inventoryClient.getProductById(item.getProductId());
            assertNotNull(product);
        });
    }

}
