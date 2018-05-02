package com.emag.service;


import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashMap;
import java.util.LinkedHashSet;

public interface ProductService {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id);
    LinkedHashSet<Product> getAllProducts();
    HashMap<Integer,Product> getProductsFromShoppingCart(String ids);
    Product getProductById(Long id);
}
