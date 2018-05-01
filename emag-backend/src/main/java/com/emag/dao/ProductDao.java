package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getAllProducts();
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id);
}
