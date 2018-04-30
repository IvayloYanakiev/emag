package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addProduct(Product product) throws ProductException {
        String addProduct = "insert into products(name,picture_url,price,middle_type_id,quantity,description) values (:name,:picture_url,:price,:middle_type_id,:quantity,:description)";
        HashMap<String,Object> params = new HashMap<>();
        params.put("name",product.getName());
        params.put("picture_url",product.getPictureURL());
        params.put("price",product.getPrice());
        params.put("middle_type_id",product.getInnerCategoryId());
        params.put("quantity",product.getQuantity());
        params.put("description",product.getDescription());
        int insertedRows = jdbcTemplate.update(addProduct,params);
        if(insertedRows==0){
            throw new ProductException("Error adding product");
        }
    }
}
