package com.example.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product Name is required")
    private String productName;

    @NotBlank(message = "Category is required")
    private String category;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private int quantity;

    @NotBlank(message = "Supplier Name is required")
    private String supplierName;
}