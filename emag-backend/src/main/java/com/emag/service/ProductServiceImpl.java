package com.emag.service;

import com.emag.dao.ProductDao;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;

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

    @Override
    public HashMap<Integer, Product> getProductsFromShoppingCart(String ids) {
        ids = ids.replace("[","");
        ids = ids.replace("]","");
        String[] idsProducts = ids.split(",");
        HashMap<Integer,Integer> products = new HashMap<>(); //how many for given product id
        for (int i = 0; i <idsProducts.length ; i++) {
            Integer productId = Integer.parseInt(idsProducts[i]);
            if(products.containsKey(productId)){
                products.put(productId,(products.get(productId)+1));
            }
            else products.put(productId,1);
        }
        return productDao.getProductsFromShoppingCart(products,idsProducts);
    }
}
