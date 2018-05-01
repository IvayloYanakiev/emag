package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashMap;
import java.util.LinkedHashSet;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
    LinkedHashSet<Product> getAllProducts();
    LinkedHashSet<Product> getProductsByInnerCategoryId(Long id);
    HashMap<Integer,Product> getProductsFromShoppingCart(HashMap<Integer, Integer> products,String[] ids);
}
