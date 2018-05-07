package com.emag.service;


import com.emag.exception.ProductException;
import com.emag.model.Product;
import java.util.LinkedHashSet;

public interface ProductService {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) throws ProductException;
    LinkedHashSet<Product> getAllProducts() throws ProductException;
    Product getProductById(Long id) throws ProductException;
    void deleteProductById(Long id) throws ProductException;

    void updateProductById(Product product) throws ProductException;

    LinkedHashSet<Product> getProductsFilteredByName(String searchInput) throws ProductException;

    LinkedHashSet<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException;

    LinkedHashSet<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException;

    LinkedHashSet<Product> getAllProductsOrderedByName(String orderIn) throws ProductException;

    LinkedHashSet<Product> getProductsFilteredByPrice(Integer maxPrice) throws ProductException;
}
