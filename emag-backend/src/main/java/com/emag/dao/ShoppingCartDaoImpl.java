package com.emag.dao;

import com.emag.config.ConstantsSQL;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Collection<Product> getProductsFromShoppingCart(ArrayList<Long> ids) throws ProductException {
        String productsInCart = ConstantsSQL.GET_PRODUCTS_BY_ID_INTERVAL;
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        LinkedHashSet<Product> retrievedProducts =  jdbcTemplate.query(productsInCart, params, new ResultSetExtractor<LinkedHashSet<Product>>() {
            @Override
            public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                while (rs.next()) {
                    try {
                        Long id = rs.getLong("id");
                        String name = rs.getString("name");
                        String pictureUrl = rs.getString("picture_url");
                        Double price = rs.getDouble("price");
                        Long middleTypeId = rs.getLong("middle_type_id");
                        Integer quantity = rs.getInt("quantity");
                        String description = rs.getString("description");
                        Integer discount = rs.getInt("discount");

                        Product product = new Product(id, name, pictureUrl, price, middleTypeId, quantity, description, discount);
                        myProducts.add(product);
                    } catch (ProductException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
                return myProducts;
            }
        });
        return retrievedProducts;
    }

}
