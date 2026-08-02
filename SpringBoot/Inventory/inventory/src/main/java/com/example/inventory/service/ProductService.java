package com.example.inventory.service;


import com.example.inventory.entity.Product;
import com.example.inventory.exception.DuplicateProductException;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Add Product
    public Product addProduct(Product product) {

        if (repository.findByProductName(product.getProductName()).isPresent()) {
            throw new DuplicateProductException("Product name already exists");
        }

        return repository.save(product);
    }

    // Update Product
    public Product updateProduct(Long id, Product product) {

        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!existing.getProductName().equals(product.getProductName())
                && repository.findByProductName(product.getProductName()).isPresent()) {

            throw new DuplicateProductException("Product name already exists");
        }

        existing.setProductName(product.getProductName());
        existing.setCategory(product.getCategory());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        existing.setSupplierName(product.getSupplierName());

        return repository.save(existing);
    }

    // Delete Product
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        repository.delete(product);
    }

    // Search by Category
    public List<Product> getByCategory(String category) {

        List<Product> products = repository.findByCategory(category);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found");
        }

        return products;
    }

    // Search by Price Range
    public List<Product> getByPriceRange(double min, double max) {

        List<Product> products = repository.findByPriceBetween(min, max);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found");
        }

        return products;
    }

    // Low Stock Products
    public List<Product> getLowStock(int quantity) {

        List<Product> products = repository.findByQuantityLessThan(quantity);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found");
        }

        return products;
    }

    // Pagination + Sorting
    public Page<Product> getProducts(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return repository.findAll(pageable);
    }
}