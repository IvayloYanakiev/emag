package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

public interface ProductDao {
    void addProduct(Product product) throws ProductException;
}
