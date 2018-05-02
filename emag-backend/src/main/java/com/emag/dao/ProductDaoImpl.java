package com.emag.dao;

import com.emag.exception.ProductException;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addProduct(Product product) throws ProductException {
        String addProduct = "insert into products(name,picture_url,price,middle_type_id,quantity,description) values (:name,:picture_url,:price,:middle_type_id,:quantity,:description)";
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("picture_url", product.getPictureURL());
        params.put("price", product.getPrice());
        params.put("middle_type_id", product.getInnerCategoryId());
        params.put("quantity", product.getQuantity());
        params.put("description", product.getDescription());
        int insertedRows = jdbcTemplate.update(addProduct, params);
        if (insertedRows == 0) {
            throw new ProductException("Error adding product");
        }
    }

    @Override
    public LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) {
        String getProducts = "select * from products where middle_type_id=:id;";
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        LinkedHashSet<Product> products = jdbcTemplate.query(getProducts, params, new ResultSetExtractor<LinkedHashSet<Product>>() {
            @Override
            public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                while (rs.next()) {
                    addProductFromResultToHashSet(rs, myProducts);
                }
                return myProducts;

            }
        });

        return products;
    }

    @Override
    public LinkedHashSet<Product> getProductsFromShoppingCart(ArrayList<Long> ids) {
        String productsInCart = "select * from products where id in (:ids)";
        HashMap<String,Object> params = new HashMap<>();
        params.put("ids",ids);

        LinkedHashSet<Product> retrievedProducts  = jdbcTemplate.query(productsInCart,params, new ResultSetExtractor<LinkedHashSet<Product>>() {

            @Override
            public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                while (rs.next()) {
                    addProductFromResultToHashSet(rs, myProducts);
                }

                return myProducts;
            }
        });
        return retrievedProducts;
    }

    private void addProductFromResultToHashSet(ResultSet rs, HashSet<Product> myProducts) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String pictureUrl = rs.getString("picture_url");
        Double price = rs.getDouble("price");
        Long middleTypeId = rs.getLong("middle_type_id");
        Integer quantity = rs.getInt("quantity");
        String description = rs.getString("description");
        int newLocationProfilePictureIndex = pictureUrl.lastIndexOf("\\");
        String newlocation = "http://127.0.0.1:8887/productPictures/" + pictureUrl.substring(newLocationProfilePictureIndex + 1);
        try {
            Product product = new Product(id, name, newlocation, price, middleTypeId, quantity, description);
            myProducts.add(product);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
    }


    public LinkedHashSet<Product> getAllProducts() {
        String getProducts = "select * from products;";
        LinkedHashSet<Product> products = jdbcTemplate.query(getProducts, new ResultSetExtractor<LinkedHashSet<Product>>() {

            @Override
            public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                while (rs.next()) {
                    addProductFromResultToHashSet(rs, myProducts);
                }
                return myProducts;
            }
        });
        return products;
    }


}
