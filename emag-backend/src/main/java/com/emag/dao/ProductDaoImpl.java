package com.emag.dao;

import com.emag.config.ConstantsSQL;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    public HashMap<Long, List<Product>> getProductsByInnerCategoryId(Long id) {
        String getProductsByInnerCategoryId = ConstantsSQL.GET_ALL_PRODUCTS_BY_INNER_CATEGORY_ID;

        HashMap<Long, List<Product>> products = jdbcTemplate.query(getProductsByInnerCategoryId, new ResultSetExtractor<HashMap<Long, List<Product>>>() {

            @Override
            public HashMap<Long, List<Product>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<Long, List<Product>> allProducts = new HashMap<>();

                while (rs.next()) {
                    try {
                        String name = rs.getString("name");
                        String pictureUrl = rs.getString("picture_url");
                        double price = rs.getDouble("price");
                        Long innerCategoryId = rs.getLong("middle_type_id");
                        int quantity = rs.getInt("quantity");
                        String description = rs.getString("description");


                        if (!allProducts.containsKey(innerCategoryId)) {
                            Product product = new Product(name, innerCategoryId, price, quantity, description, pictureUrl);
                            List<Product> catProducts = new LinkedList<>();
                            catProducts.add(product);
                            allProducts.put(innerCategoryId, catProducts);
                        }

                        allProducts.get(innerCategoryId).add(new Product(name, innerCategoryId, price, quantity, description, pictureUrl));
                    } catch (ProductException e) {
                        e.printStackTrace();
                    }
                }
                return allProducts;
            }
        });
        return products;
    }
}
