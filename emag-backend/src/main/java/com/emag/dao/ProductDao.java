package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.Collection;

public interface ProductDao {
    Collection getAllProducts() throws ProductException;
    Collection<Product> getProductsByInnerCategoryId(Long id) throws ProductException;
    Product getProductById(Long id) throws ProductException;
    Collection<Product> getProductsFilteredByName(String searchInput) throws ProductException;
    Collection<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException;
    Collection<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException;
    Collection<Product> getAllProductsOrderedByName(String orderIn) throws ProductException;
}
