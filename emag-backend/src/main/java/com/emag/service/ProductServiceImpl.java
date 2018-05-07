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
    public void addProduct(Product product) throws ProductException {
        productDao.addProduct(product);
    }

    @Override
    public LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) throws ProductException {
        return productDao.getProductsByInnerCategoryId(id);
    }

    @Override
    public LinkedHashSet<Product> getAllProducts() throws ProductException {
        return productDao.getAllProducts();
    }

    @Override
    public Product getProductById(Long id) throws ProductException {
        return productDao.getProductById(id);
    }

    @Override
    public void deleteProductById(Long id) throws ProductException {
         productDao.deleteProductById(id);
    }

    @Override
    public void updateProductById(Product product) throws ProductException {
        productDao.updateProduct(product);
    }

    @Override
    public LinkedHashSet<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException {
        return  productDao.getAllProductsOrderedByPrice(orderIn);
    }

    @Override
    public LinkedHashSet<Product> getProductsFilteredByName(String searchInput) throws ProductException {
        return productDao.getProductsFilteredByName(searchInput);
    }

    public LinkedHashSet<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException {
        return  productDao.getAllProductsOrderedByDiscount(orderIn);
    }

    @Override
    public LinkedHashSet<Product> getAllProductsOrderedByName(String orderIn) throws ProductException {
        return  productDao.getAllProductsOrderedByName(orderIn);
    }

    @Override
    public LinkedHashSet<Product> getProductsBetweenTwoPrices(Integer from, Integer to) throws ProductException {
        return  productDao.getProductsBetweenTwoPrices(from,to);
    }

}
