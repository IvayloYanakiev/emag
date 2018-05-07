package com.emag.service;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.LinkedHashSet;

public interface ShoppingCartService {
    LinkedHashSet<Product> getProductsFromShoppingCart(String ids) throws ProductException;
}
