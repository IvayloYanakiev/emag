package com.emag.service;

import com.emag.exception.ProductException;
import com.emag.model.Product;
import java.util.Collection;

public interface ShoppingCartService {
    Collection<Product> getProductsFromShoppingCart(String ids) throws ProductException;
}
