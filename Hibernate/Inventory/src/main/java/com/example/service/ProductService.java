package com.example.service;

import com.example.entity.Product;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductService {

    // Add Product
    public void insertProduct(Product product) {

        if (product.getProductName() == null ||
                product.getCategory() == null ||
                product.getSupplier() == null) {

            System.out.println("Null values are not allowed.");
            return;
        }

        if (product.getStock() < 0) {

            System.out.println("Stock cannot be negative.");
            return;
        }

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            List<Product> products = session.createQuery(
                            "from Product where productName=:name", Product.class)
                    .setParameter("name", product.getProductName())
                    .list();

            if (!products.isEmpty()) {

                System.out.println("Duplicate product found.");
                transaction.rollback();
                System.out.println("Transaction rolled back.");
                return;
            }

            session.persist(product);

            transaction.commit();

            System.out.println("Product inserted successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive())
                transaction.rollback();

            e.printStackTrace();
        }
    }

    // Update Stock

    public void updateStock(int id, int stock) {

        if (stock < 0) {

            System.out.println("Stock cannot be negative.");
            return;
        }

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Product product = session.get(Product.class, id);

            if (product == null) {

                System.out.println("Product not found.");
                transaction.rollback();
                return;
            }

            product.setStock(stock);

            session.merge(product);

            transaction.commit();

            System.out.println("Stock updated successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive())
                transaction.rollback();

            e.printStackTrace();
        }

    }

    // Delete Product

    public void deleteProduct(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Product product = session.get(Product.class, id);

            if (product == null) {

                System.out.println("Product not found.");
                transaction.rollback();
                return;
            }

            session.remove(product);

            transaction.commit();

            System.out.println("Product deleted successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive())
                transaction.rollback();

            e.printStackTrace();
        }

    }

    // Search Product

    public void searchProduct(String productName) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Product> products = session.createQuery(
                            "from Product where productName=:name", Product.class)
                    .setParameter("name", productName)
                    .list();

            if (products.isEmpty()) {

                System.out.println("Product not found.");
                return;
            }

            products.forEach(System.out::println);

        }

    }

    // Retrieve All Products

    public void getAllProducts() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Product> products =
                    session.createQuery("from Product", Product.class).list();

            if (products.isEmpty()) {

                System.out.println("No products found.");
                return;
            }

            products.forEach(System.out::println);

        }

    }

}