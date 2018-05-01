package com.emag.service;


import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashSet;

public interface ProductService {
    void addProduct(Product product) throws ProductException;
    HashSet<Product> getAllProducts();
}
