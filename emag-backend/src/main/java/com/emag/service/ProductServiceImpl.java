package com.emag.service;

import com.emag.dao.ProductDao;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public void addProduct(Product product) throws ProductException {
        productDao.addProduct(product);
    }

    @Override
    public HashMap<Long, List<Product>> getProductsByInnerCategoryId(Long id) {
        return productDao.getProductsByInnerCategoryId(id);
    }
}
