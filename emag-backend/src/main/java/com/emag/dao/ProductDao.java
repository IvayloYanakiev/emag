package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashSet;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
    HashSet<Product> getAllProducts();
}
