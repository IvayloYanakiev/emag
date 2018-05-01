package com.emag.service;


import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.HashMap;
import java.util.List;

public interface ProductService {
    void addProduct(Product product) throws ProductException;
    HashMap<Long, List<Product>> getProductsByInnerCategoryId(Long id);
}
