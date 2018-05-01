package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
    HashSet<Product> getAllProducts();
    HashMap<Long, List<Product>> getProductsByInnerCategoryId(Long id);
}
