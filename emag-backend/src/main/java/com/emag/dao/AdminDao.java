package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

public interface AdminDao {
    void addProduct(Product product) throws ProductException;
    void deleteProductById(Long id) throws ProductException;
    void updateProduct(Product product) throws ProductException;
}
