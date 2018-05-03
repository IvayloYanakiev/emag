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
        String addProduct = "insert into products(name,picture_url,price,middle_type_id,quantity,description,discount) values (:name,:picture_url,:price,:middle_type_id,:quantity,:description,:discount)";
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("picture_url", product.getPictureURL());
        params.put("price", product.getPrice());
        params.put("middle_type_id", product.getInnerCategoryId());
        params.put("quantity", product.getQuantity());
        params.put("description", product.getDescription());
        params.put("discount", product.getDiscount());
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
        LinkedHashSet<Product> products = null;
        try {
            products = getProducts(getProducts, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    private LinkedHashSet<Product> getProducts(String getProducts, HashMap<String, Object> params) throws SQLException {
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
    }

    @Override
    public LinkedHashSet<Product> getProductsFromShoppingCart(ArrayList<Long> ids) {
        String productsInCart = "select * from products where id in (:ids)";
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        LinkedHashSet<Product> retrievedProducts = null;
        try {
            retrievedProducts = getProducts(productsInCart, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            System.out.println(e.getMessage());
        }
    }


    public LinkedHashSet<Product> getAllProducts() {
        String getProducts = "select * from products;";
        LinkedHashSet<Product> products = null;
        try {
            products = getProducts(getProducts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
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
                        selectedProduct.setDiscount(rs.getInt("discount"));
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

    public void deleteProductById(Long productId) throws ProductException {
        String getProductById = "delete from products where id=:productId";
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        int rowsDeleted = jdbcTemplate.update(getProductById, params);
        if (rowsDeleted == 0) {
            throw new ProductException("Deleting product unsuccessful");
        }
    }

    @Override
    public void updateProduct(Product product) throws ProductException {
        String updateProduct = "update products set name=:name,middle_type_id=:categoryId,price=:price,quantity=:quantity,description=:description, discount=:discount where id =:id ";
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",product.getId());
        params.put("name",product.getName());
        params.put("categoryId",product.getInnerCategoryId());
        params.put("price",product.getPrice());
        params.put("quantity",product.getQuantity());
        params.put("description",product.getDescription());
        params.put("discount",product.getDiscount());
        int updatedRows = jdbcTemplate.update(updateProduct,params);
        if(updatedRows==0){
            throw new ProductException("Update failed");
        }
    }

    @Override
    public LinkedHashSet<Product> getAllProductsOrderedByPrice(String orderBy) {
        String getProducts = "select * from products order by price";
        if (orderBy.equals("asc")) {
            getProducts += " asc";
        } else {
            getProducts += " desc";
        }
        LinkedHashSet<Product> products = null;
        try {
            products = getProducts(getProducts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private LinkedHashSet<Product> getProducts(String getProducts) throws SQLException {
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
    }
}

