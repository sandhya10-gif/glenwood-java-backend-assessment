package com.example;

import com.example.entity.Product;
import com.example.service.ProductService;

public class Main {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        // Create Product
        Product product = new Product(
                "Laptop",
                "Electronics",
                65000,
                20,
                "Dell"
        );

        // Add Product
        service.insertProduct(product);

        // Retrieve All Products
        service.getAllProducts();

        // Search Product
        service.searchProduct("Laptop");

        // Update Stock
        service.updateStock(1, 25);

        // Delete Product
        service.deleteProduct(1);

    }
}