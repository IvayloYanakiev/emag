package com.emag.service;

import com.emag.dao.ProductDao;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Collection<Product> getProductsByInnerCategoryId(Long id) throws ProductException {
        return productDao.getProductsByInnerCategoryId(id);
    }

    @Override
    public Collection<Product> getAllProducts() throws ProductException {
        return productDao.getAllProducts();
    }

    @Override
    public Product getProductById(Long id) throws ProductException {
        return productDao.getProductById(id);
    }

    @Override
    public Collection<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException {
        return  productDao.getAllProductsOrderedByPrice(orderIn);
    }

    @Override
    public Collection<Product> getProductsFilteredByName(String searchInput) throws ProductException {
        return productDao.getProductsFilteredByName(searchInput);
    }

    public Collection<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException {
        return  productDao.getAllProductsOrderedByDiscount(orderIn);
    }

    @Override
    public Collection<Product> getAllProductsOrderedByName(String orderIn) throws ProductException {
        return  productDao.getAllProductsOrderedByName(orderIn);
    }

}
