package com.emag.service;

import com.emag.dao.ProductDao;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.HashSet;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public void addProduct(Product product) throws ProductException {
        productDao.addProduct(product);
    }

    @Override
    public LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) {
        return productDao.getProductsByInnerCategoryId(id);
    }

    @Override
    public LinkedHashSet<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
}
