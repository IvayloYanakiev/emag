package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public interface ShoppingCartDao {
    LinkedHashSet<Product> getProductsFromShoppingCart(ArrayList<Long> ids) throws ProductException;
}
