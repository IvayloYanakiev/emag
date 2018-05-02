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
        String getProducts = ConstantsSQL.GET_ALL_PRODUCTS_BY_INNER_CATEGORY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        LinkedHashSet<Product> products = jdbcTemplate.query(getProducts, params, new ResultSetExtractor<LinkedHashSet<Product>>() {
            @Override
            public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                while (rs.next()) {
                    addProduct(rs, myProducts);
                }
                return myProducts;

            }
        });

        return products;
    }

    @Override
    public HashMap<Integer, Product> getProductsFromShoppingCart(HashMap<Integer, Integer> products,String[] ids) {
        HashMap<Integer,Product> returnedProducts = new HashMap<>();
        System.out.println(ids.toString());
        String getProducts = "select from products where in ";
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Integer productId = entry.getKey();
            Integer howMany = entry.getValue();

        }
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        String getProductById = ConstantsSQL.GET_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);

        Product productById = jdbcTemplate.query(getProductById, params, new ResultSetExtractor<Product>() {
            @Override
            public Product extractData(ResultSet rs) throws SQLException {
                Product selectedProduct = new Product();

                while (rs.next()) {
                    try {
                        selectedProduct.setId(rs.getLong("id"));
                        selectedProduct.setName(rs.getString("name"));
                        String pictureUrl = rs.getString("picture_url");
                        selectedProduct.setPrice(rs.getDouble("price"));
                        selectedProduct.setQuantity(rs.getInt("quantity"));
                        selectedProduct.setDescription(rs.getString("description"));
                        int newLocationProfilePictureIndex = pictureUrl.lastIndexOf("\\");
                        String newlocation = "http://127.0.0.1:8887/productPictures/" + pictureUrl.substring(newLocationProfilePictureIndex + 1);
                        selectedProduct.setPictureURL(newlocation);
                    } catch (ProductException e) {
                        e.printStackTrace();
                    }
                }
                return selectedProduct;
            }
        });
        return productById;
    }


    public LinkedHashSet<Product> getAllProducts() {
        String getProducts = ConstantsSQL.GET_ALL_PRODUCTS;
        LinkedHashSet<Product> products = jdbcTemplate.query(getProducts, new ResultSetExtractor<LinkedHashSet<Product>>() {


            @Override
            public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                while (rs.next()) {
                    addProduct(rs, myProducts);
                }
                return myProducts;
            }
        });
        return products;
    }

    private void addProduct(ResultSet rs, LinkedHashSet<Product> myProducts) throws SQLException {
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

}
