package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.ArrayList;
import java.util.Collection;

public interface ShoppingCartDao {
    Collection<Product> getProductsFromShoppingCart(ArrayList<Long> ids) throws ProductException;
}
