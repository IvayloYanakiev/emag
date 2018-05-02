package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getAllProducts();
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id);
    LinkedHashSet<Product> getProductsFromShoppingCart(ArrayList<Long> ids);
    Product getProductById(Long id);
    void deleteProductById(Long id) throws ProductException;
}
