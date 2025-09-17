package com.playground.inventory_service.model;

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
public class Product extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String productCode;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    private Category category;



    public Product(String productCode, Integer quantity, BigDecimal price) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.price    = price;
    }

}
