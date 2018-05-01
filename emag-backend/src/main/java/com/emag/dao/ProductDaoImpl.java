package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

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

    @Override
    public HashSet<Product> getAllProducts() {
        String getProducts = "select * from products;";
        HashSet<Product> products = jdbcTemplate.query(getProducts, new ResultSetExtractor<HashSet<Product>>() {


            @Override
            public  HashSet<Product> extractData(ResultSet rs) throws SQLException {
                HashSet<Product> myProducts =  new HashSet<>();

                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String pictureUrl = rs.getString("picture_url");
                    Double price = rs.getDouble("price");
                    Long middleTypeId = rs.getLong("middle_type_id");
                    Integer quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    try {
                        Product product = new Product(id,name,pictureUrl,price,middleTypeId,quantity,description);
                        myProducts.add(product);
                    } catch (ProductException e) {
                        System.out.println(e.getMessage());
                    }

                }
                return myProducts;

            }
        });
        return products;
    }
}
