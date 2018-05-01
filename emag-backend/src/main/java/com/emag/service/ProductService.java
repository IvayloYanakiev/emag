package com.emag.service;


import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public interface ProductService {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id);
    LinkedHashSet<Product> getAllProducts();
}
