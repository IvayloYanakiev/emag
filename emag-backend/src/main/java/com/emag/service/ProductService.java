package com.emag.service;


import com.emag.exception.ProductException;
import com.emag.model.Product;

public interface ProductService {
    void addProduct(Product product) throws ProductException;
}
