package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getAllProducts() throws ProductException;
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) throws ProductException;
    LinkedHashSet<Product> getProductsFromShoppingCart(ArrayList<Long> ids) throws ProductException;
    Product getProductById(Long id) throws ProductException;
    void deleteProductById(Long id) throws ProductException;

    void updateProduct(Product product) throws ProductException;

    LinkedHashSet<Product> getAllProductsOrderedByPrice(String orderBy) throws ProductException;
    LinkedHashSet<Product> getProductsFilteredByName(String searchInput) throws ProductException;
}
