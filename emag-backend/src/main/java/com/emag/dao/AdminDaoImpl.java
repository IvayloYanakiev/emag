package com.emag.dao;

import com.emag.config.ConstantsErrorMessages;
import com.emag.config.ConstantsSQL;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AdminDaoImpl implements AdminDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addProduct(Product product) throws ProductException {
        String addProduct = ConstantsSQL.INSERT_INTO_PRODUCTS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("picture_url", product.getPictureURL());
        params.put("price", product.getPrice());
        params.put("middle_type_id", product.getInnerCategoryId());
        params.put("quantity", product.getQuantity());
        params.put("description", product.getDescription());
        params.put("discount", product.getDiscount());
        try {
            jdbcTemplate.update(addProduct, params);
        } catch (Exception e) {
            throw new ProductException(ConstantsErrorMessages.ERROR_ADDING_PRODUCT, e);
        }
    }

    @Override
    public void deleteProductById(Long productId) throws ProductException {
        String deleteProductById = ConstantsSQL.DELETE_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        try {
            jdbcTemplate.update(deleteProductById, params);
        } catch (Exception e) {
            throw new ProductException(ConstantsErrorMessages.UNSUCCESSFUL_PRODUCT_DELETING, e);
        }
    }

    @Override
    public void updateProduct(Product product) throws ProductException {
        String updateProduct = ConstantsSQL.UPDATE_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", product.getId());
        params.put("name", product.getName());
        params.put("categoryId", product.getInnerCategoryId());
        params.put("price", product.getPrice());
        params.put("quantity", product.getQuantity());
        params.put("description", product.getDescription());
        params.put("discount", product.getDiscount());
        try {
            jdbcTemplate.update(updateProduct, params);
        } catch (Exception e) {
            throw new ProductException(ConstantsErrorMessages.UNSUCCESSFUL_PRODUCT_UPDATING, e);
        }
    }
}
