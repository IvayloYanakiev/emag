package com.emag.dao;

import com.emag.config.ConstantsSQL;
import com.emag.exception.CategoryException;
import com.emag.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public HashMap<Long, Category> getAllCategories() throws CategoryException {

        String getAllCategories = ConstantsSQL.GET_ALL_CATEGORIES;

        HashMap<Long, Category> categories = jdbcTemplate.query(getAllCategories, new ResultSetExtractor<HashMap<Long, Category>>() {


            @Override
            public HashMap<Long, Category> extractData(ResultSet rs) throws SQLException {
                HashMap<Long, Category> myCategories = new HashMap<>();

                while (rs.next()) {
                    try {
                        Long mainID = rs.getLong("id");
                        String mainName = rs.getString("main_name");
                        Long middleID = rs.getLong("middle_id");
                        String middleName = rs.getString("middle_name");

                        if (!myCategories.containsKey(mainID)) {
                            Category mainCategory = new Category(mainID, mainName);
                            myCategories.put(mainID, mainCategory);
                        }

                        myCategories.get(mainID).addCategory(new Category(middleID, middleName));
                    } catch (CategoryException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return myCategories;

            }
        });
        return categories;
    }
}
