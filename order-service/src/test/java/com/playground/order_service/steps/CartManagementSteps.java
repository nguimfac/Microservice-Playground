package com.playground.order_service.steps;

import com.playground.constant.CartStatusEnum;
import com.playground.dto.response.ProductResponse;
import com.playground.order_service.constant.OrderServiceConstant;
import com.playground.order_service.dao.CartItemRepository;
import com.playground.order_service.dto.request.CartRequest;
import com.playground.order_service.dto.response.CartItemResponse;
import com.playground.order_service.dto.response.CartResponse;
import com.playground.order_service.service.facade.cart.CartService;
import com.playground.order_service.service.facade.feignClient.InventoryClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Transactional
public class CartManagementSteps {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private MockMvc mockMvc;

    private static List<Integer> httpStatus;

    @Given("un panier avec l'id {int} existe et appartient au user d'id {int}")
    public void unPanierAvecLIdExisteEtAppartientAuUserDId(int cartId, int userId) {
        CartRequest cartRequest = new CartRequest(cartId ,  userId , null);
        cartService.createCart(cartRequest);
        CartResponse cartResponse = cartService.findCartById(cartRequest.id());
        assertEquals(cartId, cartResponse.id(), "L'ID du panier n'est pas correct");
        //assertEquals(userId, cartResponse.ownerId(), "Le panier n'appartient pas au bon utilisateur");
        assertEquals(CartStatusEnum.INIT, cartResponse.cartStatusEnum(), "Le statut du panier doit être INIT");
    }

    @Given("Les produits suivant existent")
    public void lesProduitsSuivantExistent(DataTable dataTable) {
        List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
        ProductResponse response;
        httpStatus = new ArrayList<>();
        //
        for (Map<String, String> row : products) {
            String productCode = row.get("productCode");
            String productName = row.get("productName");
            BigDecimal price   = BigDecimal.valueOf(Double.parseDouble(row.get("price")));
            int quantity       = Integer.parseInt(row.get("quantity"));
            long productId     = Long.parseLong(row.get("productId"));
            //
             response = new ProductResponse(
                    productId,
                    productCode,
                    quantity,
                    price,
                    productName
            );
            Mockito.when(inventoryClient.getProductById(productId)).thenReturn(response);
        }
    }


    @When("j'ajoute les produits suivants au panier {int}")
    public void jAjouteLesProduitsSuivantsAuPanier(int cartId, DataTable dataTable) throws Exception {
        List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : products) {
            int productId = Integer.parseInt(row.get("productId"));
            int quantity = Integer.parseInt(row.get("quantity"));

            String json = "{ \"productId\": " + productId + ", \"quantity\": " + quantity + " }";

            int status = mockMvc.perform(
                            post("/api/order/cart/{cartId}/add" , cartId)
                                    .contentType(OrderServiceConstant.PRODUCT_REQUEST_VENDOR_TYPE)
                                    .accept(OrderServiceConstant.CART_RESPONSE_VENDOR_TYPE)
                                    .content(json)
                    )
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getStatus();

            httpStatus.add(status);
        }

    }

    @Then("le panier d'id {int} contient {int} ligne\\(s) en base de données")
    public void lePanierContientLigneSEnBaseDeDonnées(int cartId , int expectedLines) {
        List<CartItemResponse> cartItems = cartService.getCartItemsOfCart(cartId);
        assertEquals(expectedLines, cartItems.size(), "Le nombre de lignes dans le panier est incorrect");
    }


    @And("le total du panier d'id {int} en base est {double}")
    public void leTotalDuPanierDIdEnBaseEst(int cartId, double expectedTotal) {
        List<CartItemResponse> items = cartService.getCartItemsOfCart(cartId);
        double total = items.stream()
                .mapToDouble(i -> {
                    ProductResponse product = inventoryClient.getProductById(i.id());
                    return product.price().doubleValue() * i.quantity();
                })
                .sum();
        assertEquals(expectedTotal, total, 0.01, "Le total du panier est incorrect");
    }

    @Then("les réponses HTTP ont le statut {int}")
    public void laRéponseHTTPALeStatut(int status) {
        assertTrue(httpStatus.stream().allMatch(s->s == status));
    }

    @Then("le service product externe a été appelé")
    public void leServiceProductExterneAÉtéAppelé() {
        Mockito.verify(inventoryClient, Mockito.atLeastOnce()).getProductById(Mockito.anyLong());
    }


}
