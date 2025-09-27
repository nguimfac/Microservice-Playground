package com.playground.inventory_service.entities.product;

import com.playground.inventory_service.entities.audi.Auditable;
import com.playground.inventory_service.entities.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    private String productCode;
    private int quantity;
    private BigDecimal price;
    private String productName;
    @ManyToOne
    private Category category;



    public Product(String productCode, Integer quantity, BigDecimal price,String productName) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.price    = price;
        this.productName = productName;
    }

}
