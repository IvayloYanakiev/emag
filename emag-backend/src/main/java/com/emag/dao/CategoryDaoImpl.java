package com.emag.dao;

import com.emag.config.Constants;
import com.emag.exceptions.AccountException;
import com.emag.exceptions.CategoryException;
import com.emag.model.Account;
import com.emag.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class CategoryDaoImpl implements CategoryDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public HashMap<Integer,Category> getAllCategories() throws CategoryException {

        String getAllCategories = Constants.GET_ALL_CATEGORIES;

        HashMap<Integer, Category> categories = jdbcTemplate.query(getAllCategories , new ResultSetExtractor<Category>() {

            @Override
            public HashMap<Integer, Category> extractData(ResultSet rs) throws SQLException {

                while (rs.next()) {
                    try {
                      Category category = new Category(rs.getLong("id"), rs.getString("name"));
                    } catch (CategoryException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        });
        return null;
    }
}
