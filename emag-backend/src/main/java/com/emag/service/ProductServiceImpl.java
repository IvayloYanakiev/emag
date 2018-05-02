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
    public LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) {
        return productDao.getProductsByInnerCategoryId(id);
    }

    @Override
    public LinkedHashSet<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public LinkedHashSet<Product> getProductsFromShoppingCart(String ids) throws ProductException {
        ids = ids.replace("[", "");
        ids = ids.replace("]", "");

        String[] idsProducts = ids.split(",");
        HashMap<Long, Integer> products = new HashMap<>(); //how many for given product id
        ArrayList<Long> idsInList = new ArrayList<>();
        for (int i = 0; i < idsProducts.length; i++) {
            Long productId = Long.parseLong(idsProducts[i]);
            idsInList.add(productId);
            if (products.containsKey(productId)) {
                products.put(productId, (products.get(productId) + 1));
            } else products.put(productId, 1);
        }
        LinkedHashSet<Product> retrievedProducts = productDao.getProductsFromShoppingCart(idsInList);
        Iterator<Product> it = retrievedProducts.iterator();
        while (it.hasNext()) {
            Product product = it.next();
            Long productId = product.getId();
            Integer howMany = products.get(productId);
            product.setQuantity(howMany);
        }

        return retrievedProducts;
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }
}
