package com.emag.dao;

import com.emag.config.ConstantsErrorMessages;
import com.emag.config.ConstantsSQL;
import com.emag.exception.CommentException;
import com.emag.exception.ProductException;
import com.emag.model.Comment;
import com.emag.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addProduct(Product product) throws ProductException {
        String addProduct = "insert into products(name,picture_url,price,middle_type_id,quantity,description,discount) values (:name,:picture_url,:price,:middle_type_id,:quantity,:description,:discount)";
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
        } catch (DataAccessException e) {
            throw new ProductException("Error adding product", e);
        }
    }

    @Override
    public LinkedHashSet<Product> getProductsByInnerCategoryId(Long id) throws ProductException {
        String getProducts = ConstantsSQL.GET_ALL_PRODUCTS_BY_INNER_CATEGORY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        LinkedHashSet<Product> products = getProducts(getProducts, params);

        return products;
    }

    private LinkedHashSet<Product> getProducts(String getProducts, HashMap<String, Object> params) throws ProductException {
        try {
            return jdbcTemplate.query(getProducts, params, new ResultSetExtractor<LinkedHashSet<Product>>() {
                @Override
                public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                    LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                    while (rs.next()) {
                        addProductFromResultToHashSet(rs, myProducts);
                    }
                    return myProducts;

                }
            });
        } catch (Exception e) {
            throw new ProductException(e.getMessage(), e);
        }
    }

    @Override
    public LinkedHashSet<Product> getProductsFromShoppingCart(ArrayList<Long> ids) throws ProductException {
        String productsInCart = ConstantsSQL.GET_PRODUCTS_BY_ID_INTERVAL;
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        LinkedHashSet<Product> retrievedProducts = getProducts(productsInCart, params);
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
        Integer discount = rs.getInt("discount");
        int newLocationProfilePictureIndex = pictureUrl.lastIndexOf("\\");
        String newlocation = "http://127.0.0.1:8887/productPictures/" + pictureUrl.substring(newLocationProfilePictureIndex + 1);
        try {
            Product product = new Product(id, name, newlocation, price, middleTypeId, quantity, description, discount);
            myProducts.add(product);
        } catch (ProductException e) {
            throw new SQLException(e.getMessage());
        }
    }


    public LinkedHashSet<Product> getAllProducts() throws ProductException {
        String getProducts = ConstantsSQL.GET_ALL_PRODUCTS;
        LinkedHashSet<Product> products = getProducts(getProducts);

        return products;
    }

    @Override
    public Product getProductById(Long id) throws ProductException {
        String getProductById = ConstantsSQL.GET_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        Product productById = null;
        try {
            productById = jdbcTemplate.query(getProductById, params, new ResultSetExtractor<Product>() {
                @Override
                public Product extractData(ResultSet rs) throws SQLException {
                    Product selectedProduct = new Product();
                    while (rs.next()) {
                        try {
                            Long commentId = rs.getLong("comment_id");
                            if (commentId != 0) {
                                if (selectedProduct.getCommentsSize() == 0) {
                                    setProductProperties(rs, selectedProduct);
                                }
                                selectedProduct.addComment(new Comment(commentId,rs.getLong("user_id"),rs.getLong("product_id"), rs.getString("uname"),rs.getString("value"),rs.getInt("stars")));
                            } else {
                                setProductProperties(rs, selectedProduct);
                            }
                        } catch (ProductException | CommentException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return selectedProduct;
                }
            });
        } catch (Exception e) {
            throw new ProductException(e.getMessage(), e);
        }
        return productById;
    }

    private void setProductProperties(ResultSet rs, Product selectedProduct) throws ProductException, SQLException {
        selectedProduct.setId(rs.getLong("product_id"));
        selectedProduct.setName(rs.getString("name"));
        String pictureUrl = rs.getString("picture_url");
        selectedProduct.setPrice(rs.getDouble("price"));
        selectedProduct.setQuantity(rs.getInt("quantity"));
        selectedProduct.setDescription(rs.getString("description"));
        selectedProduct.setDiscount(rs.getInt("discount"));

        int newLocationProfilePictureIndex = pictureUrl.lastIndexOf("\\");
        String newlocation = "http://127.0.0.1:8887/productPictures/" + pictureUrl.substring(newLocationProfilePictureIndex + 1);
        selectedProduct.setPictureURL(newlocation);
    }

    public void deleteProductById(Long productId) throws ProductException {
        String deleteProductById = ConstantsSQL.DELETE_PRODUCT_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        try {
            jdbcTemplate.update(deleteProductById, params);
        } catch (DataAccessException e) {
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
        } catch (DataAccessException e) {
            throw new ProductException(ConstantsErrorMessages.UNSUCCESSFUL_PRODUCT_UPDATING, e);
        }
    }

    @Override
    public LinkedHashSet<Product> getAllProductsOrderedByPrice(String orderIn) throws ProductException {
        String getProducts = ConstantsSQL.ORDER_PRODUCTS_BY_PRICE;
        if (orderIn.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    @Override
    public LinkedHashSet<Product> getAllProductsOrderedByDiscount(String orderIn) throws ProductException {
        String getProducts = ConstantsSQL.ORDER_PRODUCTS_BY_DISCOUNT;
        if (orderIn.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    @Override
    public LinkedHashSet<Product> getAllProductsOrderedByName(String orderIn) throws ProductException {
        String getProducts = ConstantsSQL.ORDER_PRODUCTS_BY_NAME;
        if (orderIn.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = getProducts(getProducts);
        return products;
    }

    @Override
    public LinkedHashSet<Product> getProductsBetweenTwoPrices(Integer from, Integer to) throws ProductException {
        return null;
    }

    private LinkedHashSet<Product> getProducts(String getProducts) throws ProductException {
        try {
            return jdbcTemplate.query(getProducts, new ResultSetExtractor<LinkedHashSet<Product>>() {

                @Override
                public LinkedHashSet<Product> extractData(ResultSet rs) throws SQLException {
                    LinkedHashSet<Product> myProducts = new LinkedHashSet<>();

                    while (rs.next()) {
                        addProductFromResultToHashSet(rs, myProducts);
                    }
                    return myProducts;
                }
            });
        } catch (Exception e) {
            throw new ProductException(e.getMessage(), e);
        }
    }
}

