package com.playground.inventory_service.controller;

import com.playground.dto.request.ProductRequest;
import com.playground.dto.response.InventoryResponse;
import com.playground.dto.response.ProductResponse;
import com.playground.inventory_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/inventory")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = "application/vnd.inventory-service.InventoryResponse+json")
    public ResponseEntity<CollectionModel<EntityModel<InventoryResponse>>> isInStock(
            @RequestParam List<String> skuCodes) {
        List<InventoryResponse> responses = productService.isInStock(skuCodes);

        List<EntityModel<InventoryResponse>> models = responses.stream()
                .map(resp -> EntityModel.of(
                        resp,
                        linkTo(methodOn(ProductController.class)
                                .isInStock(List.of(resp.skuCode())))
                                .withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<InventoryResponse>> collectionModel =
                CollectionModel.of(models,
                        linkTo(methodOn(ProductController.class)
                                .isInStock(skuCodes))
                                .withRel("check-stock"));

        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping(
            consumes  = "application/vnd.inventory-service.ProductRequest+json",
            produces =  "application/vnd.inventory-service.ProductResponse+json"
    )
    public ResponseEntity<EntityModel<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse response = productService.createProduct(productRequest);
        EntityModel<ProductResponse> resource = EntityModel.of(response,
                linkTo(methodOn(ProductController.class).getProduct(response.id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts(Pageable.unpaged())).withRel("all-products")
        );
        return ResponseEntity.created(linkTo(methodOn(ProductController.class).getProduct(response.id())).toUri()).body(resource);
    }

    @GetMapping(path = "/{id}" ,produces = "application/vnd.inventory-service.ProductResponse+json")
    public EntityModel<ProductResponse> getProduct(@PathVariable long id) {
        ProductResponse productResponse = productService.getProductById(id);

        return EntityModel.of(productResponse,
                linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts(Pageable.unpaged())).withRel("all-products")
        );
    }

    @GetMapping(produces = "application/vnd.inventory-service.ProductResponse+json")
    public CollectionModel<EntityModel<ProductResponse>> getAllProducts(Pageable pageable) {
        Page<ProductResponse> page = productService.getAllProducts(pageable);

        List<EntityModel<ProductResponse>> productResources = page.stream()
                .map(p -> EntityModel.of(p,
                        linkTo(methodOn(ProductController.class).getProduct(p.id())).withSelfRel()))
                .toList();

        CollectionModel<EntityModel<ProductResponse>> collection = CollectionModel.of(productResources,
                linkTo(methodOn(ProductController.class).getAllProducts(pageable)).withSelfRel());

        // Liens pagination HATEOAS
        if (page.hasNext()) {
            collection.add(linkTo(methodOn(ProductController.class)
                    .getAllProducts(page.nextOrLastPageable())).withRel("next"));
        }
        if (page.hasPrevious()) {
            collection.add(linkTo(methodOn(ProductController.class)
                    .getAllProducts(page.previousOrFirstPageable())).withRel("prev"));
        }
        return collection;
    }
}



