package com.emag.service;

import com.emag.exception.ProductException;
import com.emag.model.Product;

public interface AdminService {
    void addProduct(Product product) throws ProductException;
    void deleteProductById(Long id) throws ProductException;
    void updateProductById(Product product) throws ProductException;
}
